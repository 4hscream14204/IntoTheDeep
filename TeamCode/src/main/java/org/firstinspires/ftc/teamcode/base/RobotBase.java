package org.firstinspires.ftc.teamcode.base;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Time;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import org.firstinspires.ftc.teamcode.roadrunner.SparkFunOTOSDrive;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Timer;
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
    public Timer timerSubsystem;

    public SparkFunOTOSDrive drive;


    public RobotBase(HardwareMap hwMap) {
        frontLeftMotor = hwMap.dcMotor.get("left_front");
        backLeftMotor = hwMap.dcMotor.get("left_back");
        frontRightMotor = hwMap.dcMotor.get("right_front");
        backRightMotor = hwMap.dcMotor.get("right_back");

        intakeSubsystem = new Intake(hwMap.servo.get("intakeServoLeft"),
                hwMap.servo.get("intakeServoRight"),
                hwMap.servo.get("gateServo"),
                hwMap.get(NormalizedColorSensor.class, "intakeColorSensor"));
        clawSubsystem = new Claw(hwMap.servo.get ("clawServo"));
        elbowSubsystem = new Elbow(hwMap.servo.get("elbowServo"));
        extensionSubsystem = new Extension(hwMap.dcMotor.get("extensionMotor"));
        shoulderSubsystem = new Shoulder(hwMap.dcMotor.get("shoulderMotor"), hwMap.digitalChannel.get("shoulderLimitSwitch"));
        wristSubsystem = new Wrist (hwMap.servo.get("wristServo"));

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        drive = new SparkFunOTOSDrive(hwMap, new Pose2d(0,0,0));
    }
}
