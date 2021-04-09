// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climb extends SubsystemBase {
  private final CANSparkMax climbL = new CANSparkMax(Constants.CLIMB_L, CANSparkMaxLowLevel.MotorType.kBrushless);
  private final CANSparkMax climbR = new CANSparkMax(Constants.CLIMB_R, CANSparkMaxLowLevel.MotorType.kBrushless);
  private final CANPIDController leftMotion = climbL.getPIDController();
  private final CANPIDController rightMotion = climbR.getPIDController();
  private final int upperLimit = 0;
  private final int lowerLimit = 0;
  private final double kP = 0;
  private final double kI = 0;
  private final double kD = 0;
  private final double kF = 0;
  private final double IZone = 0;
  private final double maxVel = 0;
  private final double allowedErr = 0;
  public Climb() {
    climbL.setIdleMode(IdleMode.kBrake);
    climbR.setIdleMode(IdleMode.kBrake);
    climbL.set(0);
    climbR.set(0);
  }
  public void rotateClimb() {
    leftMotion.setReference(upperLimit, ControlType.kSmartMotion);
    rightMotion.setReference(upperLimit, ControlType.kSmartMotion);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
