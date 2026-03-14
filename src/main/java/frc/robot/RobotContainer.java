package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.HopperRollers;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.KickerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SlapdownSubsystem;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import java.io.File;
import swervelib.SwerveInputStream;

public class RobotContainer
{
  final CommandXboxController driverXbox = new CommandXboxController(0);

  private final SwerveSubsystem drivebase = new SwerveSubsystem(
      new File(Filesystem.getDeployDirectory(), "swerve/neokraken"));

  private final SlapdownSubsystem intakeslapdown = new SlapdownSubsystem(12);
  private final IntakeSubsystem intake = new IntakeSubsystem();
  private final HopperRollers hopper = new HopperRollers();
  private final ShooterSubsystem shooter = new ShooterSubsystem();
  private final KickerSubsystem kicker = new KickerSubsystem();

  private final SendableChooser<Command> autoChooser = new SendableChooser<>();
  private boolean intakeDeployed = false;

  SwerveInputStream driveAngularVelocity = SwerveInputStream.of(
          drivebase.getSwerveDrive(),
          () -> -driverXbox.getLeftY(),
          () -> -driverXbox.getLeftX())
      .withControllerRotationAxis(() -> -driverXbox.getRightX())
      .deadband(OperatorConstants.DEADBAND)
      .scaleTranslation(0.4)
      .allianceRelativeControl(true);

  public RobotContainer()
  {
    intakeslapdown.resetEncoder();

    configureBindings();
    DriverStation.silenceJoystickConnectionWarning(true);

    autoChooser.setDefaultOption("Do Nothing", Commands.none());
    autoChooser.addOption("Drive Forward", drivebase.driveForward().withTimeout(1));

    SmartDashboard.putData("Auto Chooser", autoChooser);
  }

  private void configureBindings()
  {
    Command driveFieldOrientedAngularVelocity = drivebase.driveFieldOriented(driveAngularVelocity);
    drivebase.setDefaultCommand(driveFieldOrientedAngularVelocity);

  driverXbox.leftTrigger().onTrue(
      Commands.runOnce(() -> {
        intakeDeployed = !intakeDeployed;

        if (intakeDeployed) {
          intakeslapdown.goDown();
        } else {
          intakeslapdown.goHome();
        }
      }, intakeslapdown)
  );

// While holding left trigger: only run rollers if intake is deployed
driverXbox.leftTrigger().whileTrue(
    Commands.startEnd(
        () -> {
          if (intakeDeployed) {
            intake.run(-0.8);
          }
        },
        intake::stop,
        intake
    )
);

driverXbox.rightTrigger().whileTrue(
    Commands.sequence(
        Commands.runOnce(() -> {
            kicker.run(0.7);
            shooter.run(0.7);
        }, kicker, shooter),

        Commands.repeatingSequence(
            Commands.runOnce(() -> hopper.run(-0.7), hopper),
            Commands.waitSeconds(10.0),

            Commands.runOnce(() -> hopper.run(-0.7), hopper),
            Commands.waitSeconds(0.2)
        )
    ).finallyDo(() -> {
        hopper.stop();
        kicker.stop();
        shooter.stop();
    })
);
        }

  public Command getAutonomousCommand()
  {
    return autoChooser.getSelected();
  }

  public void setMotorBrake(boolean brake)
  {
    drivebase.setMotorBrake(brake);
  }
}