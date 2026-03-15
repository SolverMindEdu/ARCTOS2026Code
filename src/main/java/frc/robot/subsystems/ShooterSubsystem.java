package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.Logger;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
  @AutoLog
  public static class ShooterInputs {
    public double currentLeft;
    public double currentRight;
  }

  public final ShooterInputsAutoLogged inputs;

  private final SparkMax leftShooter = new SparkMax(13, MotorType.kBrushless);
  private final SparkMax rightShooter = new SparkMax(11, MotorType.kBrushless);

  private final SparkMaxConfig leftConfig;

  public ShooterSubsystem() {
    inputs = new ShooterInputsAutoLogged();

    leftConfig = new SparkMaxConfig();
    SparkMaxConfig rightConfig = new SparkMaxConfig();

    leftConfig.smartCurrentLimit(40);
    leftConfig.idleMode(SparkBaseConfig.IdleMode.kCoast);

    rightConfig.smartCurrentLimit(40);
    rightConfig.idleMode(SparkBaseConfig.IdleMode.kCoast);

    // make right follow left
    rightConfig.follow(13, true);

    leftShooter.configure(
        leftConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);

    rightShooter.configure(
        rightConfig,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {
    inputs.currentLeft = leftShooter.getOutputCurrent();
    inputs.currentRight = leftShooter.getOutputCurrent();

    Logger.processInputs("Shooter", inputs);
  }

  public void run(double speed) {
    leftShooter.set(speed);
  }

  public void stop() {
    leftShooter.stopMotor();
  }
}