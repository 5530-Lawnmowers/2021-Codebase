// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.CompressorStart;
import edu.wpi.first.wpilibj.*;
public class Pneumatic extends SubsystemBase {
  /** Creates a new Pnuematic. */
  private final Compressor compressor = new Compressor();
  private final DoubleSolenoid LeftS = new DoubleSolenoid(1,3);
  private final DoubleSolenoid RightS = new DoubleSolenoid(0,2);

  public Pneumatic() {
    setDefaultCommand(new CompressorStart(this));
  }

  @Override
  public void periodic() {
    // compressor.stop();
    // This method will be called once per scheduler run
  }
  public void intakeExtend(){
    LeftS.set(DoubleSolenoid.Value.kForward);
    RightS.set(DoubleSolenoid.Value.kForward);

  }
  public void intakeRetract(){
    LeftS.set(DoubleSolenoid.Value.kReverse);
    RightS.set(DoubleSolenoid.Value.kReverse);

  }

  public void compressorStop(){
    compressor.stop();
  }
  public void compressorStart(){
    compressor.start();
  }
}
