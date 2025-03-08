package org.firstinspires.ftc.teamcode.base;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Time;
import com.acmerobotics.roadrunner.ftc.SparkFunOTOSCorrected;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.util.ElapsedTime;

//import org.firstinspires.ftc.teamcode.roadrunner.SparkFunOTOSDrive;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Sweeper;
import org.firstinspires.ftc.teamcode.subsystems.Timer;
import org.firstinspires.ftc.teamcode.subsystems.TimerLED;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class RobotBase {

    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor backLeftMotor;
    public DcMotor backRightMotor;

    public Intake intakeSubsystem;
    public Claw clawSubsystem;
    public Elbow elbowSubsystem;
    public Extension extensionSubsystem;
    public Shoulder shoulderSubsystem;
    public Wrist wristSubsystem;
    public Chassis chassisSubsystem;
    public ITDCrabEnums.EnmAlliance alliance;
    public Timer timerSubsystem;
    public TimerLED ledSubsystem;
    public Sweeper sweeperSubsystem;

    public Follower drive;
    public ElapsedTime timer;
    public Pose startPose = new Pose(0, 0, 0);


    public RobotBase(HardwareMap hwMap) {
        timer = new ElapsedTime();
        /*frontLeftMotor = hwMap.dcMotor.get("left_front");
        backLeftMotor = hwMap.dcMotor.get("left_back");
        frontRightMotor = hwMap.dcMotor.get("right_front");
        backRightMotor = hwMap.dcMotor.get("right_back");*/
        drive = new Follower(hwMap);
        drive.setStartingPose(startPose);

        intakeSubsystem = new Intake(hwMap.servo.get("intakeServoLeft"),
                hwMap.servo.get("intakeServoRight"),
                hwMap.servo.get("gateServo"),
                hwMap.get(NormalizedColorSensor.class, "intakeColorSensor"),
                hwMap.servo.get("intakeLED"));
        clawSubsystem = new Claw(hwMap.servo.get ("clawServo"));
        elbowSubsystem = new Elbow(hwMap.servo.get("elbowServo"));
        extensionSubsystem = new Extension(hwMap.dcMotor.get("extensionLeftMotor"), hwMap.dcMotor.get("extensionRightMotor"), hwMap.digitalChannel.get("extensionLimitSwitch"));
        shoulderSubsystem = new Shoulder(hwMap.dcMotor.get("shoulderMotor"),
                hwMap.dcMotor.get("rightShoulderMotor"),
                hwMap.digitalChannel.get("shoulderLimitSwitch"));
        wristSubsystem = new Wrist (hwMap.servo.get("wristServo"));
        ledSubsystem = new TimerLED (hwMap.servo.get("timerLED"));
        chassisSubsystem = new Chassis(hwMap.dcMotor.get("left_front"), hwMap.dcMotor.get("right_front"), hwMap.dcMotor.get("left_back"), hwMap.dcMotor.get("right_back"), timer, drive);
        sweeperSubsystem = new Sweeper(hwMap.servo.get("sweeperServo"));

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
