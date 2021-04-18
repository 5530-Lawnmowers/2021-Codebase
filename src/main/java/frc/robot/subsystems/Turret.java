// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.*;
import frc.robot.helpers.LimelightHelper;
import frc.robot.helpers.ShuffleboardHelpers;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;

public class Turret extends SubsystemBase {
  public double UpperMax = .307480 - .001367; // .001367 * 6.25 // end -.332168 * 6.25
 
  public double LowerMax = -.332168 - .001367;
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
    ShuffleboardHelpers.createSimpleWidget("Turret", "Turret LowerMax", 0.306113);
    ShuffleboardHelpers.createSimpleWidget("Turret", "Turret UpperMax", -0.333535);
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
    return turretPID.calculate(LimelightHelper.getTurretRawX(), getHeadingXOffset()); 
    
  }
  /**
   * Get the require X-axis(Vertical) offset for accurate shooting
   * Equation compensates for the gatewheel side spin.
   * 
   * @return offset
   */
  public double getHeadingXOffset() {
    double FIT_A = 0;
    double offset = FIT_A * getHeading(); //function for offset
    return offset; //The + 0.3 is a just constant added to the offset equation
  }
  /**Get an offset to compensate for sideways driving velocity
   * 
   * @return offset
  */
  public double getVelocityXOffset() {
    double offset = 0;
    //some Function of Robot Velocity
    return offset;
  }
  /**
   * return the Heading of the Turret. Zero is whereever we set it. 
   * Currently set at Robot Right
   * @return Heading in degrees
   */
  public double getHeading() {
    double position = (encoder.getPosition() / 6.25); // 150T to 24T pulley thus we divide by 6.25 ratio to convert 6.25 turns into 1 turn.
    return position;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Turret Heading", getHeading());
    if( (getHeading() > UpperMax) &&  (inputPower > 0)){ //If encoder reads value above this value, turret stops
      turret.set(0);
    }
    else if ( (getHeading() <  LowerMax) && (inputPower < 0)){ //If encoder reads value below this value, turret stops
      turret.set(0);
    }
    else{
      turret.set(inputPower);
    }
    
    LowerMax = (double) ShuffleboardHelpers.getWidgetValue("Turret", "Turret LowerMax");
    UpperMax = (double) ShuffleboardHelpers.getWidgetValue("Turret", "Turret UppgerMax");
    // This method will be called once per scheduler run
  }
}
