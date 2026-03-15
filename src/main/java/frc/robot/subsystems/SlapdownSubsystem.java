package frc.robot.subsystems;

import org.littletonrobotics.junction.AutoLog;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SlapdownSubsystem extends SubsystemBase {
  @AutoLog
  public static class SlapdownInputs {
    public double current;
    public double temperature;
    public double voltage;
  }

  private final SparkMax motor;
  private final RelativeEncoder encoder;
  private final SparkClosedLoopController controller;

  public SlapdownSubsystem(int canId) {

    motor = new SparkMax(canId, MotorType.kBrushless);
    encoder = motor.getEncoder();
    controller = motor.getClosedLoopController();

    SparkMaxConfig config = new SparkMaxConfig();

    config.idleMode(SparkBaseConfig.IdleMode.kBrake);
    config.smartCurrentLimit(20);

    config.closedLoop
        .pid(0.2, 0, 0)
        .outputRange(-0.65, 0.65); // slow

    motor.configure(
        config,
        SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  public void resetEncoder() {
    encoder.setPosition(0);
  }

  public void goDown() {
    controller.setSetpoint(32, SparkBase.ControlType.kPosition);
  }

  public void goHome() {
    controller.setSetpoint(0, SparkBase.ControlType.kPosition);
  }

  public double getPosition() {
    return encoder.getPosition();
  }
}