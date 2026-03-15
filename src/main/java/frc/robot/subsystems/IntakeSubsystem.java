package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.config.SparkBaseConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

  private final SparkMax intakeMotor;
  private final SparkMaxConfig intakeConfig;

  public IntakeSubsystem() {
    intakeMotor = new SparkMax(14, MotorType.kBrushless); // change CAN ID

    intakeConfig = new SparkMaxConfig();
    intakeConfig.smartCurrentLimit(20); // TODO update current limit with a good value
    intakeConfig.idleMode(SparkBaseConfig.IdleMode.kBrake);

    intakeMotor.configure(intakeConfig, SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  public void run(double speed) {
    intakeMotor.set(speed);
  }

  public void stop() {
    intakeMotor.stopMotor();
  }
}