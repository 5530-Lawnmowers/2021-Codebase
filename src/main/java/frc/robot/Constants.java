// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int CLIMB_L = 25; //SparkMAX
    public static final int CLIMB_R = 8; //SparkMAX

    //Drivetrain motors
    public static final int DT_R1 = 9; //TalonFX
    public static final int DT_R2 = 10; //TalonFX
    public static final int DT_L1 = 11; //TalonFX
    public static final int DT_L2 = 12; //TalonFX

    //Intake Motor
    public static final int INTAKE = 20 ; //Spark NEO 550
    
    //Spindex Motor
    public static final int SPINDEX = 16; //Spark 775
    public static final int GATEWHEEL = 53;

    //Shooter Motors
    public static final int FLY_1 = 24; //SparkMAX
    public static final int FLY_2 = 30; //SparkMAX
    public static final int HOOD = 3; //Spark Brushed Motor
    public static final int TURRET = 41; //Spark NEO 550

    //Feed Sensor
    public static final int SHOOT_SENSOR = 2;

    

}
