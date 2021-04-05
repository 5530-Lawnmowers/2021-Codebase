// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class IntakeIn extends CommandBase {
  private Intake intake;
  private double intakeSet = 1.0;
  private double spindexSet = -.75;
  private Feed feed;

  public IntakeIn(Intake intake, Feed feed) {
    addRequirements(intake);
    addRequirements(feed);
    this.intake = intake;
    this.feed = feed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    intake.setIntake(intakeSet);
    feed.setSpindex(spindexSet);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.stopIntake();
    feed.stopSpindex();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
