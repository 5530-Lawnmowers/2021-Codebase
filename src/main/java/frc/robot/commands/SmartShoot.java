package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
public class SmartShoot extends CommandBase{
    
    private Shooter shooter;
    private Feed feed;
    private double accel = 1.0;
    private double backDrive = -0.2; 
    private double TARGET = 4500;
    private double THRESHOLD = 4450;
    private double spindexFeed = 1.0;
    private double gateWheelFeed = 0.75;
    private double shoot = 0.9;
    public SmartShoot(Shooter shooter, Feed feed) {
        addRequirements(shooter);
        addRequirements(feed);
        this.shooter = shooter;
        this.feed = feed;
    }
    public void initialize() {

    }
    public void execute() {
        if (shooter.getVelocity() < THRESHOLD) {
            shooter.setShooter(accel);
            if (feed.getBreakbeam()) {
              feed.setGateWheel(backDrive);
              feed.setSpindex(spindexFeed);
            } else {
              feed.stopGateWheel();
            }
          } else if (shooter.getVelocity() < TARGET) {
            shooter.setShooter(shoot);
          } else {
            shooter.setShooter(shoot);
            feed.setGateWheel(gateWheelFeed);
          }

    }
    public void end(boolean interrupted) {
        shooter.stopShooter();
        feed.stopGateWheel();
        feed.stopSpindex();
    } 
}
