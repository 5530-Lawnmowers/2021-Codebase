// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;


public class Flywheel extends SubsystemBase {
  private CANSparkMax flywheel1 = new CANSparkMax(Constants.FLY_1, CANSparkMaxLowLevel.MotorType.kBrushless);
  private CANSparkMax flywheel2 = new CANSparkMax(Constants.FLY_2, CANSparkMaxLowLevel.MotorType.kBrushless);
  /** Creates a new Shooter. */
  public Flywheel() {
    flywheel1.setIdleMode(IdleMode.kCoast);
    flywheel2.setIdleMode(IdleMode.kCoast);

  }
  public void setShooter(double speed){
    flywheel1.set(speed);
    flywheel2.set(-speed);

  }
  public void stopShooter(){
    flywheel1.set(0);
    flywheel2.set(0);
  }
  public double getVelocity() {
    return flywheel1.getEncoder().getVelocity();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Flywheel", getVelocity());
    // This method will be called once per scheduler run
  }
}
