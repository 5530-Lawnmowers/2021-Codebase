package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
public class SmartShoot extends CommandBase{
    
    private Shooter shooter;
    private Feed feed;
    private double accel = 1;
    private double shoot = 0.9;
    private double TARGET = 3250;
    private double THRESHOLD = 3200;
    private double spindexFeed = 0.5;
    private double gateWheelFeed = .2;
    private double gateWheelSpeedFeed = 1;

    public SmartShoot(Shooter shooter, Feed feed) {
        addRequirements(shooter, feed);
        this.shooter = shooter;
        this.feed = feed;
    }
    public void initialize() {

    }
    public void execute() {
        
      if (shooter.getVelocity() < THRESHOLD) { //Flywheel < 4500
          shooter.setShooter(accel);//Sets Shooter voltage to .9
         
        if (feed.getBreakbeam()) { // If the breakbeam returns true(no ball)
          feed.setGateWheel(gateWheelFeed); //feeds the balls up the gatewheel
          feed.setSpindex(spindexFeed); // feeds the balls toward the gatewheel
        } else { //If the breakbeam returns false
          feed.stopGateWheel(); //stops feeding the balls
          feed.stopSpindex();
        }
      } else if (shooter.getVelocity() < TARGET) { //4450 <= Flywheel Speed < Target
        shooter.setShooter(shoot); //Set flyhweel to 0.85 output
      } else {//Flywheel >= 4500
        shooter.setShooter(shoot); //Sets flywheel to 0.9 output
        feed.setGateWheel(gateWheelSpeedFeed); //Feeds cells toward the shooter
        feed.setSpindex(spindexFeed);
      }
    }
    public void end(boolean interrupted) {
        shooter.stopShooter();
        feed.stopGateWheel();
        feed.stopSpindex();
    } 
}
