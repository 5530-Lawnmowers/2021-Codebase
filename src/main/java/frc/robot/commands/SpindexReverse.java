// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class SpindexReverse extends CommandBase {
  /** Creates a new StartSpin. */
  private Feed feed;
  private double spindexSet = -.5; //negative away from shooter
  public SpindexReverse(Feed feed) {
    
    addRequirements(feed);
    this.feed = feed;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    feed.setSpindex(spindexSet);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    feed.stopSpindex();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}