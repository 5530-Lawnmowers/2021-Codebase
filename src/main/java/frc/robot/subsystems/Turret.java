// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.*;
import frc.robot.helpers.LimelightHelper;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;

public class Turret extends SubsystemBase {
  public final static double UpperMax = 42;
 
  public final static double LowerMax = -159;
  public double inputPower = 0;
  private CANSparkMax turret = new CANSparkMax(Constants.TURRET, CANSparkMaxLowLevel.MotorType.kBrushless);
  private final PIDController turretPID = new PIDController(.05, 0, .0007);
  public CANEncoder encoder;
  public double PIDPower = 0;
  

  /** Creates a new Turret. */
  public Turret() {
    turret.setIdleMode(IdleMode.kBrake);
    turret.setSmartCurrentLimit(40);
    turret.set(0);
    encoder = turret.getEncoder();
    setDefaultCommand(new TurretManual(this));
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
    return PIDPower;
  }

  @Override
  public void periodic() {
    PIDPower = turretPID.calculate(LimelightHelper.getTurretRawX(), 0);
    System.out.println(encoder.getPosition());
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
