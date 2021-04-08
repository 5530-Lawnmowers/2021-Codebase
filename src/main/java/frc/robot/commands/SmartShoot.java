package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
public class SmartShoot extends CommandBase{
    
    private Shooter shooter;
    private Feed feed;
    private double accel = 1.0;
    private double shoot = 0.9;
    private double TARGET = 4500;
    private double THRESHOLD = 4450;
    private double spindexFeed = .5;
    private double gateWheelFeed = 1;

    public SmartShoot(Shooter shooter, Feed feed) {
        addRequirements(shooter, feed);
        this.shooter = shooter;
        this.feed = feed;
    }
    public void initialize() {

    }
    public void execute() {
        
      if (shooter.getVelocity() < THRESHOLD) { //Flywheel < 4500
          shooter.setShooter(accel);//Sets Shooter voltage to 1.0 
         
        if (feed.getBreakbeam()) { // If the breakbeam returns true(clear)
          feed.setGateWheel(gateWheelFeed); //feeds the balls up
          feed.setSpindex(spindexFeed); // feeds the balls toward the gatewheel
        } else { //If the breakbeam returns false
          feed.stopGateWheel(); //stops feeding the balls
          feed.stopSpindex();
        }
          
      } else if (shooter.getVelocity() < TARGET) { //4450 <= Flywheel Speed < Target
        shooter.setShooter(shoot); //Set flyhweel to 0.9 output
      } else {//Flywheel >= 4500
        shooter.setShooter(shoot); //Sets flywheel to 0.9 output
        feed.setGateWheel(gateWheelFeed); //Feeds cells toward the shooter
        feed.setSpindex(spindexFeed);
      }
    }
    public void end(boolean interrupted) {
        shooter.stopShooter();
        feed.stopGateWheel();
        feed.stopSpindex();
    } 
}
