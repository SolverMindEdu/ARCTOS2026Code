package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HopperRollers extends SubsystemBase {

  private final SparkMax hoppermotor = new SparkMax(15, MotorType.kBrushless); // change CAN ID

  public HopperRollers() {}

  public void run(double speed) {
    hoppermotor.set(speed);
  }

  public void stop() {
    hoppermotor.stopMotor();
  }
}