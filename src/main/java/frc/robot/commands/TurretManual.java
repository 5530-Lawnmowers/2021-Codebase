// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

import edu.wpi.first.wpilibj.GenericHID;
import frc.robot.RobotContainer;



public class TurretManual extends CommandBase {
  /** Creates a new StartSpin. */
  private Turret turret;
  //private double turretSet = -.75; //Motor voltage percent output
  public TurretManual(Turret turret) {
    
    addRequirements(turret);
    this.turret = turret;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Math.abs(RobotContainer.XBController2.getX(GenericHID.Hand.kLeft)) > .4){
    turret.setTurret(-RobotContainer.XBController2.getX(GenericHID.Hand.kLeft));
    }
    else{
      turret.setTurret(0);

    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    turret.stopTurret();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
