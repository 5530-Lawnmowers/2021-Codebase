// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import frc.robot.Constants;
import frc.robot.helpers.ShuffleboardHelpers;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;


public class Flywheel extends SubsystemBase {
  private CANSparkMax flywheel1 = new CANSparkMax(Constants.FLY_1, CANSparkMaxLowLevel.MotorType.kBrushless);
  private CANSparkMax flywheel2 = new CANSparkMax(Constants.FLY_2, CANSparkMaxLowLevel.MotorType.kBrushless);
  private CANPIDController flywheel1PID = flywheel1.getPIDController();
  private CANPIDController flywheel2PID = flywheel2.getPIDController();
  private double kP = 1E-3;
  private double kI = 0;
  private double kD = 0;
  private double kFF = .00022;
  /** Creates a new Shooter. */
  public Flywheel() {
    flywheel1.setIdleMode(IdleMode.kCoast);
    flywheel2.setIdleMode(IdleMode.kCoast);
    flywheel1PID.setP(kP);
    flywheel1PID.setI(kI);
    flywheel1PID.setD(kD);
    flywheel1PID.setFF(kFF);
    flywheel2PID.setP(kP);
    flywheel2PID.setI(kI);
    flywheel2PID.setD(kD);
    flywheel2PID.setFF(kFF);
    //Shuffleboard Quick PID Tuning 
    ShuffleboardHelpers.createSimpleWidget("FlyWheel", "kP", 5E-5);
    // ShuffleboardHelpers.createSimpleWidget("FlyWheel", "kI", 0);
    // ShuffleboardHelpers.createSimpleWidget("FlyWheel", "kD", 0);
    // ShuffleboardHelpers.createSimpleWidget("FlyWheel", "kFF", .0000156);
    // ShuffleboardHelpers.createSimpleWidget("FlyWheel", "Real kP", 5E-5);
  }
  public void setShooter(double speed){
    flywheel1.set(speed);
    flywheel2.set(-speed);

  }
  public void stopShooter(){
    flywheel1.set(0);
    flywheel2.set(0);
  }
  public void shooterVelocityPID(double setPoint) {

    flywheel1PID.setReference(setPoint, ControlType.kVelocity);
    flywheel2PID.setReference(-setPoint, ControlType.kVelocity);
  }
  public double getVelocity() {
    return flywheel1.getEncoder().getVelocity();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Velocity", getVelocity());
    SmartDashboard.putNumber("Voltage", flywheel1.get());
    SmartDashboard.putNumber("VelocityFlywheel2", flywheel1.getEncoder().getVelocity());
    //Useful Info for PID Loop
    // ShuffleboardHelpers.createSimpleWidget("FlyWheel", "Flywheel Voltage", flywheel1.get());
    kP = (double) ShuffleboardHelpers.getWidgetValue("FlyWheel", "kP");
    //ShuffleboardHelpers.setWidgetValue("FlyWheel", "Rea; kP", kP);
    // kI = ShuffleboardHelpers.getWidgetValue("FlyWheel", "kI");
    // kD = ShuffleboardHelpers.getWidgetValue("FlyWheel", "kD");
    // kFF = ShuffleboardHelpers.getWidgetValue("FlyWheel", "kFF");
    // ShuffleboardHelpers.createSimpleWidget("FlyWheel", "Flywheel Velocity", getVelocity());
    // This method will be called once per scheduler run
  }
}
