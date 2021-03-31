// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;
public class Feed extends SubsystemBase {
  private CANSparkMax spindex = new CANSparkMax(Constants.SPINDEX, CANSparkMaxLowLevel.MotorType.kBrushed);

  /** Creates a new Feed. */
  public Feed() {
    spindex.setIdleMode(IdleMode.kBrake); //set how the motor behaves Idle
    spindex.setSmartCurrentLimit(30); //Prevents Explosion
  }
  public void setSpin (double speed) {
    spindex.set(speed);
  }
  /**Stops the Intake motor */
  public void stopSpin() {
    spindex.set(0);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
