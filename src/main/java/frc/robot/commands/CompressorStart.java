// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Pneumatic;

public class CompressorStart extends CommandBase {
  /** Creates a new StartCompressor. */
  private final Pneumatic compressor;
  public CompressorStart(Pneumatic compressor) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(compressor);
    this.compressor = compressor;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(RobotContainer.XBController2.getPOV() != -1 ){
        compressor.compressorStart();      
      }
      else{
        compressor.compressorStop();
  
      }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    compressor.compressorStop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
