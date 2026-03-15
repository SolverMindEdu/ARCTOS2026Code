package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.Logger;

import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HopperRollers extends SubsystemBase {
  @AutoLog
  public static class HopperInput {
    public double current;
    public double temperature;
    public double voltage;
  }

  public final HopperInputAutoLogged inputs;

  private final SparkMax hoppermotor = new SparkMax(15, MotorType.kBrushless); // change CAN ID

  public HopperRollers() {
    inputs = new HopperInputAutoLogged();
  }

  public void run(double speed) {
    hoppermotor.set(speed);
  }

  public void periodic() {
    // Update the values
    inputs.current = hoppermotor.getOutputCurrent();
    inputs.temperature = hoppermotor.getMotorTemperature();
    inputs.voltage = hoppermotor.getBusVoltage() * hoppermotor.getAppliedOutput();

    Logger.processInputs("Hopper", inputs);
  }

  public void stop() {
    hoppermotor.stopMotor();
  }
}