// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.helpers.LimelightHelper;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;
import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;

public class Hood extends SubsystemBase {

  private final CANSparkMax hood = new CANSparkMax(Constants.HOOD, CANSparkMaxLowLevel.MotorType.kBrushed);
  private final PIDController hoodPID = new PIDController(.4, 0, 0, 10);
  private final DutyCycleEncoder encoder = new DutyCycleEncoder(Constants.HOOD_ENCODER);
  private double lowerLimit = -.05;
  public double upperLimit = 1.1;
  private double inputPower = 0;

  /** Creates a new Hood. */
  public Hood() {
    hood.setIdleMode(IdleMode.kBrake); //set how the motor behaves Idle
    hood.setSmartCurrentLimit(30); //Prevents Explosion
    hood.set(0); // Sets the speed to 0
    setDefaultCommand(new HoodManual(this));

  }
  /**Sets hood speed */
  public void setHood(double speed) {
    //System.out.println(hood.get());

    // if(encoder.getPosition() > upperLimit && speed > 0){
    //   hood.set(0);
    // }
    inputPower = speed;
    hood.set(inputPower);
  }
  /**
   * Stops hood motor
   */
  public void stopHood() {
    hood.set(0);
  }
  /**
   * Aims the hood based on empirically derived ball drop equation offset. Compenstates for ball flight path.
   * @return Hood speed determined based on PID loop from current limelight Y-Axis Offset to required Y-Axis Offset
   */
  public double hoodPIDCalculate() {
    double limelightArea = LimelightHelper.getTurretRawA(); // gets the raw curret value of limelight target Area.
    double limelightYOffset = LimelightHelper.getTurretRawY(); // gets the raw current value of limelight Y-axis offset to target. Measurement parameter for PID loop.
    double shootingYOffset = getShootingYOffset(limelightArea); //sets the target Y-axis offset for the given target area. Target point for the PID loop
    //System.out.println(shootingYOffset);
    return hoodPID.calculate(limelightYOffset, shootingYOffset); //returns the output voltage percentage for motor
  }
  
  /**
   * Get the require Y-axis(Vertical) offset for accurate shooting
   * Equation compensates for the drop of the ball due to resistance.
   * @param limelightA The Area of the limelight target
   * @return 
   */
  public double getShootingYOffset(double limelightA) {
    final double FIT_A = -9.039;
    final double FIT_B = 0.4935;
    double offset;
    if (limelightA <=0) {
      return 0;
    }
    offset = -FIT_A * Math.log(FIT_B * limelightA);
    return offset + 0.3 - 4-2; //The + 0.3 is a just constant added to the offset equation
  }

  // public double getAngle() {
    // return hoodEncoder.getPosition();
  // }
  public void setZero() {
    encoder.reset();
  }


  
  @Override
  public void periodic() {
    SmartDashboard.putNumber("Hood Position",encoder.get());
    // System.out.println(encoder.getPosition());
    // This method will be called once per scheduler run
    // if( (encoder.get() > upperLimit) &&  (inputPower > 0)){ //If encoder reads value above this value, turret stops
    //   hood.set(0);
    // }
    // else if ( (encoder.get() <  lowerLimit) && (inputPower < 0)){ //If encoder reads value below this value, turret stops
    //   hood.set(0);
    // }
    // else{
    //   hood.set(inputPower);
    // }
  }
}
