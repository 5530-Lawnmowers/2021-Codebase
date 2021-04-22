// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;

public class ClimbRelease extends CommandBase {
  /** Creates a new Climb. */
  private final double setPointL = 1.2; // Positive is arm up
  private final double setPointR = 1.6; // Positive is arm up
  private final Climb climb;
  public ClimbRelease(Climb climb) {
    addRequirements(climb);
    this.climb = climb;
    // Use addRequirements() here to declare subsystem dependencies.
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    climb.getPIDController(setPointL, setPointR);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climb.stopClimb();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if ( (climb.getLPosition() > setPointL-.2) && (climb.getRPosition() > setPointR-.2)) {
      return true;
    }
    return false;
  }
}
