// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;
public class Feed extends SubsystemBase {
  private CANSparkMax spindex = new CANSparkMax(Constants.SPINDEX, CANSparkMaxLowLevel.MotorType.kBrushed);
  private CANSparkMax gateWheel = new CANSparkMax(Constants.GATEWHEEL, CANSparkMaxLowLevel.MotorType.kBrushless);

private final DigitalInput shootSensor = new DigitalInput(Constants.SHOOT_SENSOR);

  /** Creates a new Feed. */
  public Feed() {
    spindex.setIdleMode(IdleMode.kBrake); //set how the motor behaves Idle
    spindex.setSmartCurrentLimit(30); //Prevents Explosion
  }
  /**Sets the Spindex Speed 
  */
  public void setSpindex (double speed) {
    spindex.set(-speed); //Positive is toward shooter
  }

  /**Stops the Spindex motor */
  public void stopSpindex() {
    spindex.set(0);

  }
  /**Sets the gatewheelspeed */
  public void setGateWheel(double speed){
    gateWheel.set(-speed); //postive is Feeding Turret
  }
  /**Stops the gateWheel */
  public void stopGateWheel(){
    gateWheel.set(0);
  }
  /**Returns the state of the Shoot Breakbeam {@code true} if clear, {@code false} if not 
   * 
   * @return boolean
  */
  public boolean getBreakbeam() {
    boolean state = !shootSensor.get();
    return state;
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Breakbeam", getBreakbeam());
    // This method will be called once per scheduler run
  }
}
