// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.helpers.LimelightHelper;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.robot.commands.*;
import frc.robot.helpers.rumbleHelp;
import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;

public class Hood extends SubsystemBase {
  public CANEncoder encoder;

  private final CANSparkMax hood = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushed);
  private final PIDController hoodPID = new PIDController(.4, 0, 0, 10);
 
  private int lowerLimit;
  public double upperLimit;

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
    hood.set(speed);
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
    return offset + 0.3 - 4; //The + 0.3 is a just constant added to the offset equation
  }
  


  
  @Override
  public void periodic() {
    if(LimelightHelper.getTurretRawY() - getShootingYOffset(LimelightHelper.getTurretRawY()) <= .2){
      rumbleHelp.setHoodAlign(true);
    }
    else{
      rumbleHelp.setHoodAlign(false);
    }
    // System.out.println(encoder.getPosition());
    // This method will be called once per scheduler run
  }
}
