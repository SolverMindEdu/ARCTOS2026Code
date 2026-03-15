package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HopperRollers extends SubsystemBase {

  private final SparkMax hopperMotor;
  private final SparkMaxConfig hopperConfig;

  public HopperRollers() {
    hopperMotor = new SparkMax(15, MotorType.kBrushless); // change CAN ID

    hopperConfig = new SparkMaxConfig();
    hopperConfig.smartCurrentLimit(20); // TODO update current limit with a good value
    hopperConfig.idleMode(SparkBaseConfig.IdleMode.kBrake);

    hopperMotor.configure(hopperConfig, SparkBase.ResetMode.kResetSafeParameters,
        SparkBase.PersistMode.kPersistParameters);
  }

  public void run(double speed) {
    hopperMotor.set(speed);
  }

  public void stop() {
    hopperMotor.stopMotor();
  }
}