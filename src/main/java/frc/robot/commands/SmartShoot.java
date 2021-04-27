package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
public class SmartShoot extends CommandBase{
    
    private Flywheel shooter;
    private Feed feed;
    private double accel = 1;
    private double shoot = 0.85;
    private double SLOWSPEED = 1000;
    private double TARGET = 3250;
    private double THRESHOLD = 3200;
    private double spindexFeed = 0.5;
    private double gateWheelFeedSlow = .1;
    private double gateWheelFeed = .2;
    private double gateWheelFeedFast = 1;

    public SmartShoot(Flywheel shooter, Feed feed) {
        addRequirements(shooter, feed);
        this.shooter = shooter;
        this.feed = feed;
    }
    public void initialize() {

    }
    public void execute() {
      if (shooter.getVelocity() < SLOWSPEED) { // at slowspeed of flwheel only move gatewheel to reduce the chance of jamming multiple balls into the system
        shooter.setShooter(accel);
        if (feed.getBreakbeam()) { // If the breakbeam returns true(no ball)
          feed.setGateWheel(gateWheelFeedSlow); //feeds the balls up the gatewheel
        } else { //If the breakbeam returns false
          feed.stopGateWheel(); //stops feeding the balll
        }
      }
      
      else if (shooter.getVelocity() < THRESHOLD) { //at THRESHOLD Speed begin to move the spindex aswell and speed up the gatewheel. This is to reduce the chance of jamming while maximizing shoot speed.
          shooter.shooterVelocityPID(TARGET);//Sets Shooter set point to Target velocity
         
        if (feed.getBreakbeam()) { // If the breakbeam returns true(no ball)
          feed.setGateWheel(gateWheelFeed); //feeds the balls up the gatewheel
          feed.setSpindex(spindexFeed); // feeds the balls toward the gatewheel
        } else { //If the breakbeam returns false
          feed.stopGateWheel(); //stops feeding the balls
          feed.stopSpindex();
        }
      } 
      
      else if (shooter.getVelocity() < TARGET-25) { //THRESHOLD <= Flywheel Speed < TARGET start feeding the balls quickly to gain shooting velocity
        shooter.shooterVelocityPID(TARGET); //Shoots balls at target-15rpm
      } 
      
      else {//Flywheel >= 4500
        shooter.setShooter(shoot); //Sets flywheel to 0.9 output
        feed.setGateWheel(gateWheelFeedFast); //Feeds cells toward the shooter
        feed.setSpindex(spindexFeed);
      }
    }
    public void end(boolean interrupted) {
        shooter.stopShooter();
        feed.stopGateWheel();
        feed.stopSpindex();
    } 
}
