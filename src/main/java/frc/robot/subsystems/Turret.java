// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.helpers.LimelightHelper;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;

public class Turret extends SubsystemBase {
  public final static double UpperMax = 700;
 
  public final static double LowerMax = 0;
  public double inputPower = 0;
  private CANSparkMax turret = new CANSparkMax(Constants.TURRET, CANSparkMaxLowLevel.MotorType.kBrushless);
  private final PIDController turretPID = new PIDController(.01, .007, .007);
  private final DutyCycleEncoder headingAbs = new DutyCycleEncoder(Constants.HOOD);
  public CANEncoder encoder;
  

  /** Creates a new Turret. */
  public Turret() {
    turret.setIdleMode(IdleMode.kBrake);
    turret.setSmartCurrentLimit(40);
    turret.set(0);
    encoder = turret.getEncoder();
  }
  /**Set turret speed */
  public void setTurret(double speed) {
    inputPower = speed;
  }
  /**Stops Turret */
  public void stopTurret() {
    turret.set(0);
  }
  /** Changes the turret heading to get the limelightXOffset to 0.
   * @return Turret speed from PID Loop 
   */
  public double turretPIDCalculate() {
    double limelightXOffset = LimelightHelper.getTurretRawX();
    return turretPID.calculate(limelightXOffset, 0);
  }
  public double getHeadingAbs() {
    return headingAbs.get();
  }
  @Override
  public void periodic() {
    if( (encoder.getPosition() > UpperMax) &&  (inputPower > 0)){
      turret.set(0);
    }
    else if ( (encoder.getPosition() <  LowerMax) && (inputPower < 0)){
      turret.set(0);
    }
    else{
      turret.set(inputPower);
    }
    
    // This method will be called once per scheduler run
  }
}
