// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.ClimbManual;

public class Climb extends SubsystemBase {
  private final CANSparkMax climbL = new CANSparkMax(Constants.CLIMB_L, CANSparkMaxLowLevel.MotorType.kBrushless);
  private final CANSparkMax climbR = new CANSparkMax(Constants.CLIMB_R, CANSparkMaxLowLevel.MotorType.kBrushless);
  private final CANPIDController climbController = climbL.getPIDController();
  private final int upperLimit = 2;
  private final int lowerLimit = 0;
  private final double kP = 5E-6;
  private final double kI = 0;
  private final double kD = 0;
  private final double kFF = 0;
  
  public Climb() {
    climbL.setIdleMode(IdleMode.kBrake);
    climbR.setIdleMode(IdleMode.kBrake);
    climbL.set(0);
    climbR.set(0);
    //setDefaultCommand(new ClimbManual(this));
  }
  public void setClimb(double speed) {
    climbL.set(-speed);
    //climbR.follow(climbL, true);
  }
  public void PID() {
    climbController.setP(kP);
    climbController.setI(kI);
    climbController.setD(kD);
    climbController.setFF(kFF);
    climbController.setReference(-2, ControlType.kPosition);

  }
  public double getPosition() {
    return climbL.getEncoder().getPosition();
  }
  public void setZero() {
    climbL.getEncoder().setPosition(0);
    climbR.getEncoder().setPosition(0);
  }
  public void stopClimb() {
    climbL.set(0);
    climbR.set(0);
  }
  
  @Override
  public void periodic() {
    SmartDashboard.putNumber("ClimbL Position", getPosition());
    SmartDashboard.putNumber("ClimbR Position", climbR.getEncoder().getPosition());
    
    // This method will be called once per scheduler run
  }
}
