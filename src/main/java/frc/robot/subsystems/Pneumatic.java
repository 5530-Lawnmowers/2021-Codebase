// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.*;
public class Pneumatic extends SubsystemBase {
  /** Creates a new Pnuematic. */
  private final Compressor comp = new Compressor();
  private final DoubleSolenoid LeftS = new DoubleSolenoid(1,3);
  private final DoubleSolenoid RightS = new DoubleSolenoid(0,2);

  public Pneumatic() {}

  @Override
  public void periodic() {
    comp.stop();
    // This method will be called once per scheduler run
  }
  public void extend(){
    LeftS.set(DoubleSolenoid.Value.kForward);
    RightS.set(DoubleSolenoid.Value.kForward);

  }
  public void retract(){
    LeftS.set(DoubleSolenoid.Value.kReverse);
    RightS.set(DoubleSolenoid.Value.kReverse);

  }

  public void stop(){
    comp.stop();
  }
  public void start(){
    comp.start();
  }
}
