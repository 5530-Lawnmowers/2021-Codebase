// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.*;

public class Shooter extends SubsystemBase {
  private CANSparkMax shoot1 = new CANSparkMax(Constants.FLY_1, CANSparkMaxLowLevel.MotorType.kBrushless);
  private CANSparkMax shoot2 = new CANSparkMax(Constants.FLY_2, CANSparkMaxLowLevel.MotorType.kBrushless);
  /** Creates a new Shooter. */
  public Shooter() {

  }
  public void run(){
    shoot1.set(1);
    shoot2.set(-1);
  }
  public void stop(){
    shoot1.set(0);
    shoot2.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
