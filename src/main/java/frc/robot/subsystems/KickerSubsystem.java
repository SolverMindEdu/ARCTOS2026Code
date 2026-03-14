package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class KickerSubsystem extends SubsystemBase {

  private final SparkMax kickermotor = new SparkMax(10, MotorType.kBrushless); // change CAN ID

  public KickerSubsystem() {}

  public void run(double speed) {
    kickermotor.set(speed);
  }

  public void stop() {
    kickermotor.stopMotor();
  }
}