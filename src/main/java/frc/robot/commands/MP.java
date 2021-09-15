// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class MP extends CommandBase {
  private Drivetrain drivetrain;
  /** Creates a new MP. */
  public MP(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.reset();
    drivetrain.startMotionProfile();
    drivetrain.control();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    drivetrain.control();

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return drivetrain.isDone();
  }
}
