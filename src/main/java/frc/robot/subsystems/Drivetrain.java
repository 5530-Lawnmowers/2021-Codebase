/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import edu.wpi.first.wpilibj.util.Units;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import com.ctre.phoenix.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.sensors.PigeonIMU;

import frc.robot.commands.CurvatureDriveNew;
import frc.robot.helpers.CSVHelper;
import frc.robot.helpers.ShuffleboardHelpers;
import org.opencv.core.RotatedRect;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;
import frc.robot.RobotContainer;
//import frc.robot.helpers.ShuffleboardHelpers;
import com.ctre.phoenix.motorcontrol.can.*;
import com.kauailabs.navx.frc.AHRS;
//comment

public class Drivetrain extends SubsystemBase {
    //Drive test
    private CSVHelper _profile;

    private MotionProfileStatus _status = new MotionProfileStatus();
    private int _state = 0;
    private int _loopTimeout = -1;
    private boolean _bStart = false;
    private SetValueMotionProfile _setValue = SetValueMotionProfile.Disable;
    private static final int k_MIN_POINTS_IN_TALON = 5;
    private static final int k_NUM_LOOPS_TIMEOUT = 10;


    private final float TickPerRev = 0;
    private final double driveMultiplier = 0.9;
    private final float WheelRadius = 0;
    private final WPI_TalonFX drivetrainLeft1 = new WPI_TalonFX(Constants.DT_L1);
    private final WPI_TalonFX drivetrainLeft2 = new WPI_TalonFX(Constants.DT_L2);
    private final WPI_TalonFX drivetrainRight1 = new WPI_TalonFX(Constants.DT_R1);
    private final WPI_TalonFX drivetrainRight2 = new WPI_TalonFX(Constants.DT_R2);

    //Drive test
    private final SpeedControllerGroup drivetrainLeft;
    private final SpeedControllerGroup drivetrainRight;
    private final DifferentialDrive diffDrive;

    public static double StartingPose;

    public static float WheelCircumference = (float) .160;//in m
    // public static DifferentialDriveOdometry DDO = new DifferentialDriveOdometry(new Rotation2d());
    //public static PigeonIMU pigeon = new PigeonIMU(15);
    // public static Rotation2d heading = new Rotation2d();
    public static float leftDistance = 0;
    public static float rightDistance = 0;
    // private final AHRS gyro = new AHRS(SerialPort.Port.kMXP);

    /**
     * Creates a new Drivetrain.
     */
    public Drivetrain() {
        drivetrainLeft1.configFactoryDefault();
        drivetrainLeft2.configFactoryDefault();
        drivetrainRight1.configFactoryDefault();
        drivetrainRight2.configFactoryDefault();
        drivetrainLeft1.setInverted(false);
        drivetrainLeft2.setInverted(false);
        drivetrainRight1.setInverted(false);
        drivetrainRight2.setInverted(false);

        drivetrainLeft1.setNeutralMode(NeutralMode.Brake);
        drivetrainLeft2.setNeutralMode(NeutralMode.Brake);
        drivetrainRight1.setNeutralMode(NeutralMode.Brake);
        drivetrainRight2.setNeutralMode(NeutralMode.Brake);

        drivetrainLeft1.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);
        drivetrainRight1.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);
        drivetrainLeft1.setSelectedSensorPosition(0);
        drivetrainRight1.setSelectedSensorPosition(0);

//        drivetrainLeft2.set(ControlMode.Follower, Constants.DT_L2);
//        drivetrainRight2.set(ControlMode.Follower, Constants.DT_R2);
        drivetrainRight1.config_kF(0, 0, 10);
        drivetrainRight1.config_kP(0, 0, 10);
        drivetrainRight1.config_kI(0, 0, 10);
        drivetrainRight1.config_kD(0, 0, 10);

        drivetrainLeft1.config_kF(0, 0, 10);
        drivetrainLeft1.config_kP(0, 0, 10);
        drivetrainLeft1.config_kI(0, 0, 10);
        drivetrainLeft1.config_kD(0, 0, 10);

        drivetrainRight2.config_kF(0, .05, 10);
        drivetrainRight2.config_kP(0, .01, 10);
        drivetrainRight2.config_kI(0, .007, 10);
        drivetrainRight2.config_kD(0, .007, 10);

        drivetrainLeft2.config_kF(0, .05, 10);
        drivetrainLeft2.config_kP(0, .01, 10);
        drivetrainLeft2.config_kI(0, .007, 10);
        drivetrainLeft2.config_kD(0, .007, 10);

        drivetrainLeft = new SpeedControllerGroup(drivetrainLeft1, drivetrainLeft2);
        drivetrainRight = new SpeedControllerGroup(drivetrainRight1, drivetrainRight2);
        diffDrive = new DifferentialDrive(drivetrainLeft, drivetrainRight);

        // gyro.zeroYaw();
        //setDefaultCommand(new ThrottleMotorTest(this)); //Use this for motor tests
        setDefaultCommand(new CurvatureDriveNew(this));

    }

    @Override
    public void periodic() {
        drivetrainRight2.setVoltage(drivetrainRight1.getMotorOutputVoltage());
        drivetrainLeft2.setVoltage(drivetrainLeft1.getMotorOutputVoltage());

//        ShuffleboardHelpers.setWidgetValue("Hood", "Left Position", drivetrainLeft1.getSelectedSensorPosition());

        // This method will be called once per scheduler run
        ShuffleboardHelpers.setWidgetValue("Drivetrain", "Left Encoder", drivetrainLeft1.getSelectedSensorPosition());
        //
        ShuffleboardHelpers.setWidgetValue("Drivetrain", "Right Encoder", drivetrainRight1.getSelectedSensorPosition());

        ShuffleboardHelpers.setWidgetValue("Drivetrain", "lV1", drivetrainLeft1.getMotorOutputVoltage());
        //
        ShuffleboardHelpers.setWidgetValue("Drivetrain", "lV2", drivetrainLeft2.getMotorOutputVoltage());
        ShuffleboardHelpers.setWidgetValue("Drivetrain", "rV1", drivetrainRight1.getMotorOutputVoltage());
        //
        ShuffleboardHelpers.setWidgetValue("Drivetrain", "rV2", drivetrainRight2.getMotorOutputVoltage());
        ShuffleboardHelpers.setWidgetValue("Drivetrain", "T_Left Encoder", 0);
        ShuffleboardHelpers.setWidgetValue("Drivetrain", "T_Right Encoder", drivetrainRight1.getActiveTrajectoryPosition());

    }

    /**
     * Sers the neutral mode for all drivetrain motors
     * @param brake {@code true} to set drivetrain to brake, {@code false} to set drivetrain to coast
     */
    public void setBrakeMode(boolean brake) {
        if (true) {
            drivetrainLeft1.setNeutralMode(NeutralMode.Brake);
            drivetrainLeft2.setNeutralMode(NeutralMode.Brake);
            drivetrainRight1.setNeutralMode(NeutralMode.Brake);
            drivetrainRight2.setNeutralMode(NeutralMode.Brake);
        } else {
            drivetrainLeft1.setNeutralMode(NeutralMode.Coast);
            drivetrainLeft2.setNeutralMode(NeutralMode.Coast);
            drivetrainRight1.setNeutralMode(NeutralMode.Coast);
            drivetrainRight1.setNeutralMode(NeutralMode.Coast);
        }
    }

    //Drive test only
    public void testDrive(double throttle, double turn) {
        if (Math.abs(throttle) > 1)
            throttle = Math.abs(throttle) / throttle; // if the value given was too high, set it to the max
        throttle *= driveMultiplier; // scale down the speed


        if (Math.abs(turn) > 1)
            turn = Math.abs(turn) / turn; // if the value given was too high, set it to the max
        turn *= driveMultiplier; // scale down the speed

        diffDrive.arcadeDrive(throttle, turn); // function provided by the  controls y and turn speed at the same time.
    }

    //Drive test only
    public void testDriveStop() {
        drivetrainLeft.stopMotor();
        drivetrainRight.stopMotor();
    }

    private double getThrottle() {
        double n = RobotContainer.XBController1.getTriggerAxis(GenericHID.Hand.kRight) -
                RobotContainer.XBController1.getTriggerAxis(GenericHID.Hand.kLeft);
        return Math.abs(n) < 0.1 ? 0 : n;
    }

    private double getTurn() {
        double n = RobotContainer.XBController1.getX(GenericHID.Hand.kLeft);
        return Math.abs(n) < 0.1 ? 0 : n;
    }

    /**
     * Set the speed of a drivetrain motor
     *
     * @param speed      The speed to set
     * @param controller Constants.DT_L1, DT_L2, DT_R1, DT_R2
     */
    public void setDrivetrainMotor(double speed, int controller) {
        if (controller == Constants.DT_L1) {
            drivetrainLeft1.set(speed);
        } else if (controller == Constants.DT_L2) {
            drivetrainLeft2.set(speed);
        } else if (controller == Constants.DT_R1) {
            drivetrainRight1.set(speed);
        } else if (controller == Constants.DT_R2) {
            drivetrainRight2.set(speed);
        }
    }

    public void setVelocity(double left, double right) {
        drivetrainRight1.set(ControlMode.Velocity, MetersToUnits(right));
        drivetrainRight2.set(ControlMode.Velocity, MetersToUnits(right));
        drivetrainLeft1.set(ControlMode.Velocity, MetersToUnits(left));
        drivetrainLeft2.set(ControlMode.Velocity, MetersToUnits(left));
    }

    public double MetersToUnits(double meters) {
        meters = Units.metersToInches(meters);
        meters = meters / (WheelRadius * Math.PI);
        meters = meters * TickPerRev;
        meters = meters / 10;
        return meters;
    }

    // public double getDistanceLeft() {
    //     leftDistance = (float) (drivetrainLeft1.getSelectedSensorPosition());
    //     leftDistance = leftDistance / TickPerRev;
    //     leftDistance = leftDistance * WheelCircumference;
    //     return leftDistance;
    // }

    // public double getDistanceRight() {
    //     rightDistance = (float) (drivetrainRight1.getSelectedSensorPosition());
    //     rightDistance = rightDistance / TickPerRev;
    //     rightDistance = rightDistance * WheelCircumference;
    //     return leftDistance;
    // }
/*
    public void resetSensors() {
        DDO.resetPosition(new Pose2d(), new Rotation2d());
        drivetrainLeft1.setSelectedSensorPosition(0);
        drivetrainRight1.setSelectedSensorPosition(0);
    }

    public static double getPigeonCompassHeading() {
        if (getPigeonYaw() < 0) {
            return 360 - (getPigeonYaw() % 360.0 + 360.0);
        }
        return 360 - (getPigeonYaw() % 360.0);
    }

    public static double getPigeonYaw() {
        double[] output = new double[3];
        pigeon.getYawPitchRoll(output);
        return output[0];
    }

    public static double getModPigeonYaw() {
        if (getPigeonCompassHeading() - StartingPose < 0) {
            return getPigeonCompassHeading() - StartingPose + 360;
        }
        return getPigeonCompassHeading() - StartingPose;
    }

    public void updatePose() {
        DDO.update(new Rotation2d(Units.degreesToRadians(getModPigeonYaw())), Units.inchesToMeters(getDistanceLeft()), Units.inchesToMeters(getDistanceRight()));
    }

    public Pose2d getPose() {
        updatePose();
        return DDO.getPoseMeters();
    }

    public void initPose(double start) {
        StartingPose = start;
    }
*/
    /**
     * Stops all drivetrain motors
     */
    public void testDrivetrainStop() {
        drivetrainLeft1.stopMotor();
        drivetrainLeft2.stopMotor();
        drivetrainRight1.stopMotor();
        drivetrainRight2.stopMotor();
    }

    // public int getLeftEncoder() {
    //     return (int)drivetrainLeft1.getSelectedSensorPosition();
    // }
    public void reset() {
        drivetrainLeft1.changeMotionControlFramePeriod(5);
        drivetrainRight1.changeMotionControlFramePeriod(5);

        drivetrainLeft1.clearMotionProfileTrajectories();
        drivetrainRight1.clearMotionProfileTrajectories();
        _loopTimeout = -1;
        _state = 0;
        _bStart = false;
        drivetrainLeft1.config_kP(0, 0.05);
        drivetrainRight1.config_kP(0, 0.05);
        drivetrainLeft1.set(ControlMode.MotionProfile, getSetValue().value);
        drivetrainRight1.set(ControlMode.MotionProfile, getSetValue().value);
        _profile = new CSVHelper("13.csv");

    }
    public void control() {
        drivetrainRight1.set(ControlMode.MotionProfile, getSetValue().value);
        drivetrainLeft1.set(ControlMode.MotionProfile, getSetValue().value);
        drivetrainLeft1.getMotionProfileStatus(_status);
        drivetrainRight1.getMotionProfileStatus(_status);
        drivetrainRight1.processMotionProfileBuffer();
        drivetrainLeft1.processMotionProfileBuffer();

       System.out.println( getSetValue().value);
        if (_loopTimeout < 0) {

        } else {

            if (_loopTimeout == 0) {
                System.out.println("Something's run");
            } else {
                --_loopTimeout;
            }
        }

        if (drivetrainRight1.getControlMode() != ControlMode.MotionProfile || drivetrainLeft1.getControlMode() != ControlMode.MotionProfile) {
            System.out.println("NO PASS");
            _state = 0;
            _loopTimeout = k_NUM_LOOPS_TIMEOUT;
        } else {
            switch(_state ) {
                case 0:
                    //Waiting for start to Motion Profile and if we are starting then start filling the talon
                    if (_bStart ) {
                        _bStart = false;
                        _setValue = SetValueMotionProfile.Disable;
                        startFilling(_profile, _profile.getLength());
                        _state = 1;
                        _loopTimeout = -1;
                        System.out.println("STATE 0");
                    }
                    break;
                case 1:
                    //Checking if we have enough points in the talon to start
                    if (true) {
                        _setValue = SetValueMotionProfile.Enable;
                        _state = 2;
                        _loopTimeout = k_NUM_LOOPS_TIMEOUT;
                        System.out.println("STATE 1");

                    }
                    break;
                case 2:
                    //Checking to make sure there are still enough points in the talon buffer
                    if (_status.isUnderrun == false) {
                        _loopTimeout = k_NUM_LOOPS_TIMEOUT;
                    }


                    //Checking if the motion profile is done
                    if (_status.activePointValid && _status.isLast) {
                        _setValue = SetValueMotionProfile.Hold;
                        _state = 0;
                        _loopTimeout = -1;

                    }
                    break;
            }
        }

    }


    private double convertToTicks(double inches ) {
        return 1024 * (inches / (6 * Math.PI));
    }

    private double convertVelocity(double inchesPerSecond){
        return 1024 * (inchesPerSecond / (60 * Math.PI));
    }

    private void startFilling (CSVHelper profile, int totalCnt) {
        TrajectoryPoint rightPoint = new TrajectoryPoint();
        TrajectoryPoint leftPoint = new TrajectoryPoint();
        if (_status.hasUnderrun) {
            System.out.println("Has Underrun");
            drivetrainRight1.clearMotionProfileHasUnderrun(0);
            drivetrainLeft1.clearMotionProfileHasUnderrun(0);

        }
        drivetrainRight1.clearMotionProfileTrajectories();
      drivetrainLeft1.clearMotionProfileTrajectories();

        for (int i = 0; i < totalCnt; ++i) {
            rightPoint.position = -(profile.getRightDistance(i));
            rightPoint.velocity = -(profile.getRightVelocity(i));
            leftPoint.position = (profile.getLeftDistance(i));
            leftPoint.velocity = (profile.getLeftVelocity(i));
            //TODO: Check this profile Slot Select
            rightPoint.profileSlotSelect0 = 0;
            rightPoint.headingDeg = 0;
            rightPoint.zeroPos = false;
//             rightPoint.timeDur = 10;
//            leftPoint.timeDur = 10;

            leftPoint.profileSlotSelect0 = 0;
            leftPoint.headingDeg = 0;
            leftPoint.zeroPos = false;


            // leftPoint.timeDur = TrajectoryDuration.Trajectory_Duration_10ms;
            if (i == 0) {
                rightPoint.zeroPos = true;
                leftPoint.zeroPos = true;
            }



            rightPoint.isLastPoint = false;
            leftPoint.isLastPoint = false;
            if ((i + 1) == totalCnt) {
                rightPoint.isLastPoint = true;
                leftPoint.isLastPoint = true;
            }
            System.out.println("Pusing point: " + rightPoint.velocity + ", " + leftPoint.velocity);

            drivetrainRight1.pushMotionProfileTrajectory(rightPoint);
            drivetrainLeft1.pushMotionProfileTrajectory(leftPoint);
        }
    }

    public void startMotionProfile() {

        drivetrainRight1.configMotionProfileTrajectoryPeriod(10, 30);
        drivetrainLeft1.configMotionProfileTrajectoryPeriod(10, 30);
        _bStart = true;
    }

    public SetValueMotionProfile getSetValue() {
        return _setValue;
    }

    public boolean isDone(){
        return _status.activePointValid && _status.isLast;
    }
    public void stop(){
        drivetrainLeft1.set(ControlMode.PercentOutput, 0);
        drivetrainRight1.set(ControlMode.PercentOutput, 0);

    }

    // public int getRightEncoder() {
    //     return (int)drivetrainRight1.getSelectedSensorPosition();
    // }

}