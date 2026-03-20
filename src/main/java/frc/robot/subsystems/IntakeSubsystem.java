package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

  private final SparkMax intakeMotor = new SparkMax(14, MotorType.kBrushless); // change CAN ID

  public IntakeSubsystem() {}

  public void run(double speed) {
    intakeMotor.set(speed);
  }

  public void stop() {
    intakeMotor.stopMotor();
  }
}