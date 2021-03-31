// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;

public class Hood extends SubsystemBase {
  /** Creates a new Hood. */
  private final CANSparkMax hood = new CANSparkMax(Constants.HOOD, CANSparkMaxLowLevel.MotorType.kBrushed);
  
  private double hoodSet;

  public Hood() {
    hood.setIdleMode(IdleMode.kBrake); //set how the motor behaves Idle
    hood.setSmartCurrentLimit(30); //Prevents Explosion
    this.hoodSet = 0; // Sets the speed to 0
  }
  public void setHood(double speed) {
    hood.set(speed);
  }
  public void stopHood() {
    hood.set(0);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
