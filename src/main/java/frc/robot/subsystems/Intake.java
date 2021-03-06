// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;

public class Intake extends SubsystemBase {
  
  //Intake has One 775Pro
  private CANSparkMax intake = new CANSparkMax(Constants.INTAKE, CANSparkMaxLowLevel.MotorType.kBrushed);
  
  /** Creates a new Intake. */
  public Intake() {
    intake.setIdleMode(IdleMode.kBrake); //set how the motor behaves Idle
    intake.setSmartCurrentLimit(30); //Prevents Explosion
    intake.set(0);
  }
  /**Sets the speed of the intake Motor */
  public void setIntake (double speed) {
    intake.set(speed);
  }
  /**Stops the Intake motor */
  public void stopIntake() {
    intake.set(0);

  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
