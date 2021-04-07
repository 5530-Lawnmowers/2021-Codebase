// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.helpers;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

/** Add your docs here. */


public class rumbleHelp {
    public static boolean hoodAlign = false;
    public static boolean turretAlign = false;
    public static boolean flySpeed = false;

    public static void setHoodAlign(boolean status) {
        hoodAlign = status;
    }
    public static void setTurretAlign(boolean status) {
        turretAlign = status;
    }
    public static void setFly(boolean status) {
        flySpeed = status;
    }
    public static void updateRumble(){
    if(flySpeed && hoodAlign && turretAlign){
        RobotContainer.XBController2.setRumble(RumbleType.kLeftRumble, .25);
        RobotContainer.XBController2.setRumble(RumbleType.kRightRumble, .25);
    }

    else{
        RobotContainer.XBController2.setRumble(RumbleType.kLeftRumble, 0);
        RobotContainer.XBController2.setRumble(RumbleType.kRightRumble, 0);
    }
    
}
public static void stopRumble(){
    RobotContainer.XBController2.setRumble(RumbleType.kLeftRumble, 0);
    RobotContainer.XBController2.setRumble(RumbleType.kRightRumble, 0);
}
}
