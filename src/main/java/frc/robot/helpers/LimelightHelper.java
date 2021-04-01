package frc.robot.helpers;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


/**Helps get Limlight Values and calculates distance to target */
public class LimelightHelper {
    private static final double TARGET_HEIGHT = 23.25; //Vertical Distance from Limelight to the goal(center of the goal)


    public static double getTurretRawY() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight-turret"); // Gets the table of limelight values
        NetworkTableEntry ty = table.getEntry("ty"); // Gets the Veritcal Offset From Crosshair To Target, -24.85 to 24.85
        double y = ty.getDouble(0.0);
        return y;
    }
    public static double getTurretRawX() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight-turret"); // Gets the table of limelight values
        NetworkTableEntry tx = table.getEntry("tx"); // Gets the Horizontal Offset From Crosshair To Target, -29.8 tp 29.8
        double x = tx.getDouble(0.0);
        return x;
    }
    public static double getTurretRawA() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight-turret"); // Gets the table of limelight values
        NetworkTableEntry ta = table.getEntry("ta"); // Gets the Target Area (0.0 to 1.0)
        double a = ta.getDouble(0.0);
        return a;
    }
    public static double getIntakeRawY() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight-intake"); // Gets the table of limelight values
        NetworkTableEntry ty = table.getEntry("ty"); // Gets the Veritcal Offset From Crosshair To Target, -24.85 to 24.85
        double y = ty.getDouble(0.0);
        return y;
    }
    public static double getIntakeRawX() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight-intake"); // Gets the table of limelight values
        NetworkTableEntry tx = table.getEntry("tx"); // Gets the Horizontal Offset From Crosshair To Target, -29.8 tp 29.8
        double x = tx.getDouble(0.0);
        return x;
    }
    public static double getIntakeRawA() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight-intake"); // Gets the table of limelight values
        NetworkTableEntry ta = table.getEntry("ta"); // Gets the Target Area (0.0 to 1.0)
        double a = ta.getDouble(0.0);
        return a;
    }
    /**Horizontonal component of distance from the limelight to the target port hole
     * @param angleOffset :physical mounting angle of limelight from horizontal in degrees
     * @return Horizontal distance to target
    */
    public static double getDistance(double angleOffset) {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight-intake"); // Gets the table of limelight values
        NetworkTableEntry ty = table.getEntry("ty"); // Gets the Veritcal Offset From Crosshair To Target, -24.85 to 24.85
        double theta = ty.getDouble(0.0) + angleOffset; // true physical angle of the limelight
        double distance = TARGET_HEIGHT / Math.tan(Math.toRadians(theta)); // Distance = vertical/tan(theta)
        return distance;
    }


}
