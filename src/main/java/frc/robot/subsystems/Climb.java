// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.*;

public class Climb extends SubsystemBase {
  private final CANSparkMax climbL = new CANSparkMax(Constants.CLIMB_L, CANSparkMaxLowLevel.MotorType.kBrushless);
  private final CANSparkMax climbR = new CANSparkMax(Constants.CLIMB_R, CANSparkMaxLowLevel.MotorType.kBrushless);
  
  private final CANPIDController climbControllerL = climbL.getPIDController();
  private final CANPIDController climbControllerR =climbR.getPIDController();
  private double armUp = 1.2;
  private boolean pressed =false;
  private double kP = .5;
  private double kI = 0;
  private double kD = 0;
  private double kFF = 0.00002;
  private double kIz = 0;
  
  public Climb() {
    climbL.setIdleMode(IdleMode.kBrake);
    climbR.setIdleMode(IdleMode.kBrake);
    climbL.set(0);
    climbR.set(0);
    setDefaultCommand(new ClimbManual(this));
  }
  public void setClimb(double speed) {
    climbL.set(-speed);
    //climbR.follow(climbL, true);
  }
  /**
   * Uses the SparkMAX onboard PID Loop and set's the refrence point using a simple position loop.
   */
  public void getPIDController() {
    climbControllerL.setP(kP);
    climbControllerL.setI(kI);
    climbControllerL.setD(kD);
    climbControllerL.setFF(kFF);
    climbControllerL.setIZone(kIz);
    climbControllerL.setOutputRange(-.25, .25);
    climbControllerL.setReference(-armUp, ControlType.kPosition);

    climbControllerR.setP(.12);
    climbControllerR.setI(0);
    climbControllerR.setD(kD);
    climbControllerR.setFF(kFF);
    climbControllerR.setIZone(kIz);
    climbControllerR.setOutputRange(-.25, .25);
    climbControllerR.setReference(1.6, ControlType.kPosition);

  }
  /**
   * Gets the encoder position from the NEO HAL sensor. NEEDS TO BE ZEROED 
   */
  public double getPosition() {
    return climbL.getEncoder().getPosition();
  }
  /**
   * Sets the NEO HAL encoder to read 0.
   */
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
    if(RobotContainer.XBController2.getAButton() || pressed){
      pressed=true;
      climbL.set(RobotContainer.XBController2.getTriggerAxis(GenericHID.Hand.kRight));
      climbR.set(-RobotContainer.XBController2.getTriggerAxis(GenericHID.Hand.kRight));
    }
    SmartDashboard.putNumber("ClimbL",getPosition());//sends data to the Dashboard. Read only.
    SmartDashboard.putNumber("ClimbR",climbR.getEncoder().getPosition());

    // This method will be called once per scheduler run
  }
}
