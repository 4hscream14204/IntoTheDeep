package org.firstinspires.ftc.teamcode.base;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Time;
import com.acmerobotics.roadrunner.ftc.SparkFunOTOSCorrected;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
//import org.firstinspires.ftc.teamcode.subsystems.Intake;

public class RobotBase {

    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor backLeftMotor;
    public DcMotor backRightMotor;
    public DcMotor intakeMotor;

    //public Intake intakeSubsystem;
    public Chassis chassisSubsystem;
    public Lift liftSubsystem;

    /*public SparkFunOTOS otos;
    public ElapsedTime timer;*/
    //public Pose startPose = new Pose(0, 0, 0);


    public RobotBase(HardwareMap hwMap) {
        //timer = new ElapsedTime();
        intakeMotor = hwMap.dcMotor.get("intakeMotor");
        liftSubsystem = new Lift(hwMap.dcMotor.get("liftMotor"), hwMap.servo.get("bucketServo"));
        /*frontLeftMotor = hwMap.dcMotor.get("left_front");
        backLeftMotor = hwMap.dcMotor.get("left_back");
        frontRightMotor = hwMap.dcMotor.get("right_front");
        backRightMotor = hwMap.dcMotor.get("right_back");*/
        //Constants.setConstants(FConstants.class, LConstants.class);
        //otos = hwMap.get(SparkFunOTOS.class, "sensor_otos");

        /*intakeSubsystem = new Intake(hwMap.servo.get("intakeServoLeft"),
                hwMap.servo.get("intakeServoRight"),
                hwMap.servo.get("gateServo"),
                hwMap.get(NormalizedColorSensor.class, "intakeColorSensor"),
                hwMap.servo.get("intakeLED"));
        /*clawSubsystem = new Claw(hwMap.servo.get ("clawServo"));
        elbowSubsystem = new Elbow(hwMap.servo.get("elbowServo"));
        extensionSubsystem = new Extension(hwMap.dcMotor.get("extensionLeftMotor"), hwMap.dcMotor.get("extensionRightMotor"), hwMap.digitalChannel.get("extensionLimitSwitch"));
        shoulderSubsystem = new Shoulder(hwMap.dcMotor.get("shoulderMotor"),
                hwMap.dcMotor.get("rightShoulderMotor"),
                hwMap.digitalChannel.get("shoulderLimitSwitch"));
        wristSubsystem = new Wrist (hwMap.servo.get("wristServo"));
        ledSubsystem = new TimerLED (hwMap.servo.get("timerLED"));*/
        chassisSubsystem = new Chassis(hwMap.dcMotor.get("leftFront"), hwMap.dcMotor.get("rightFront"), hwMap.dcMotor.get("leftRear"), hwMap.dcMotor.get("rightRear"));
        //sweeperSubsystem = new Sweeper(hwMap.servo.get("sweeperServo"));

        /*frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);*/

    }
}
