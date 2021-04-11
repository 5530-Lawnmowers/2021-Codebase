// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.helpers.*;
import frc.robot.commands.*;
import frc.robot.helpers.LimelightHelper;
import edu.wpi.first.wpilibj.controller.PIDController;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;

public class Turret extends SubsystemBase {
  public final static double UpperMax = 42;
 
  public final static double LowerMax = -159;
  public double inputPower = 0;
  private CANSparkMax turret = new CANSparkMax(Constants.TURRET, CANSparkMaxLowLevel.MotorType.kBrushless); 
  private final PIDController turretPID = new PIDController(.05, 0, .0007);
  public CANEncoder encoder;
  

  /** Creates a new Turret. */
  public Turret() {
    turret.setIdleMode(IdleMode.kBrake);
    turret.setSmartCurrentLimit(40);
    turret.set(0);
    encoder = turret.getAlternateEncoder(AlternateEncoderType.kQuadrature, 8192); //sets encoder to get data from the data port absolute encoder
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
  public double getPID() {
    return turretPID.calculate(LimelightHelper.getTurretRawX(), 0); 
    
  }
  /**
   * Get the require X-axis(Vertical) offset for accurate shooting
   * Equation compensates for the gatewheel side spin.
   * 
   * @return offset
   */
  public double getHeadingXOffset() {
    final double FIT_A = -9.039;
    final double FIT_B = 0.4935;
    double offset;
    offset = -FIT_A * Math.log(FIT_B * encoder.getPosition());
    return offset + 0.3 - 4-2; //The + 0.3 is a just constant added to the offset equation
  }
  public double getVelocityXOffset() {
    double offset = 0;
    //some Function of Robot Velocity
    return offset;
  }

  @Override
  public void periodic() {
    System.out.println(encoder.getPosition());
    if( (encoder.getPosition() > UpperMax) &&  (inputPower > 0)){ //If encoder reads value above this value, turret stops
      turret.set(0);
    }
    else if ( (encoder.getPosition() <  LowerMax) && (inputPower < 0)){ //If encoder reads value below this value, turret stops
      turret.set(0);
    }
    else{
      turret.set(inputPower);
    }
    
    
    // This method will be called once per scheduler run
  }
}
