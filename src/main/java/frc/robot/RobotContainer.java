// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  public static final XboxController XBController1 = new XboxController(1);
  //Driver Controller
  public static final XboxController XBController2 = new XboxController(2);
  //Operator Controller
  // The robot's subsystems and commands are defined here...

  public static JoystickButton xb1a = new JoystickButton(XBController1, 1);
  public static JoystickButton xb1b = new JoystickButton(XBController1, 2);
  public static JoystickButton xb1y = new JoystickButton(XBController1, 4);
  public static JoystickButton xb1x = new JoystickButton(XBController1, 3);
  public static JoystickButton xb1lb = new JoystickButton(XBController1, 5); 
  public static JoystickButton xb1rb = new JoystickButton(XBController1, 6); 
  public static JoystickButton xbstart = new JoystickButton(XBController1, 8);


  public static JoystickButton xb2a = new JoystickButton(XBController2, 1);
  public static JoystickButton xb2b = new JoystickButton(XBController2, 2);
  public static JoystickButton xb2lb = new JoystickButton(XBController2, 5);
  public static JoystickButton xb2rb = new JoystickButton(XBController2, 6);
  public static JoystickButton xb2y = new JoystickButton(XBController2, 4);
  public static JoystickButton xb2x = new JoystickButton(XBController2, 3);
  public static JoystickButton xb2start = new JoystickButton(XBController2, 8);
  public static JoystickButton xb2back = new JoystickButton(XBController2, 7);
  public static JoystickButton xb2lstick = new JoystickButton(XBController2, 9);
  public static JoystickButton xb2rstick = new JoystickButton(XBController2, 10);
  private final Climb climb = new Climb();
  private final Pneumatic pneumatic = new Pneumatic();

  private final Drivetrain drivetrain = new Drivetrain();
  private final Intake intake = new Intake();
  private final Shooter shooter = new Shooter();
  private final Feed feed = new Feed();
  private final Turret turret = new Turret();
  private final Hood hood = new Hood();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    xb2rstick.toggleWhenPressed(new PneumaticExtend(pneumatic));
    xb2x.toggleWhenPressed(new IntakeIn(intake));
    //xb2a.whenHeld(new FeedUp(feed));
    xb2y.whenHeld(new SpindexReverse(feed));
    xb2start.whenHeld(new SmartShoot(shooter, feed));
    xb2rb.whenHeld(new runFly(shooter));
    xb2lb.whenHeld(new AlignAll(turret, hood));
    xb2a.whenHeld(new ClimbPID(climb));
    xb2b.whenHeld(new ClimbSetZero(climb));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
}
