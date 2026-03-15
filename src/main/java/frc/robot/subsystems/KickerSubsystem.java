package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class KickerSubsystem extends SubsystemBase {

  private final SparkMax kickerMotor; // change CAN ID
  private final SparkMaxConfig kickerConfig;

  public KickerSubsystem() {
    kickerMotor = new SparkMax(10, MotorType.kBrushless);

    kickerConfig = new SparkMaxConfig();
    kickerConfig.smartCurrentLimit(20); // TODO update to a real current limit value
    kickerConfig.idleMode(SparkBaseConfig.IdleMode.kBrake);

    kickerMotor.configure(kickerConfig, SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  public void run(double speed) {
    kickerMotor.set(speed);
  }

  public void stop() {
    kickerMotor.stopMotor();
  }
}