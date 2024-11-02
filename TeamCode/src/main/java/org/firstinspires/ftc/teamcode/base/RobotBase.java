package org.firstinspires.ftc.teamcode.base;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.roadrunner.SparkFunOTOSDrive;
import org.firstinspires.ftc.teamcode.subsystems.Bucket;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.SpecimenGrabber;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.firstinspires.ftc.teamcode.subsystems.Zlide;

public class RobotBase {

    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor backLeftMotor;
    public DcMotor backRightMotor;
    public IMU imu;

    public Intake intakeSubsystem;
    public Bucket bucketSubsystem;
    public Zlide zlideSubsystem;
    public Wrist wristSubsystem;
    public Lift liftSubsystem;
    public SpecimenGrabber specimenGrabberSubsystem;
    public SparkFunOTOSDrive drive;





    public RobotBase(HardwareMap hwMap) {
        Gamepad.LedEffect rgbEffect = new Gamepad.LedEffect.Builder()
                .addStep(1, 0, 0, 500) // Show red for 250ms
                .addStep(0, 1, 0, 500) // Show green for 250ms
                .addStep(0, 0, 1, 500) // Show blue for 250ms
                .addStep(1, 1, 1, 500) // Show white for 250ms
                .build();


        imu = hwMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);

        frontLeftMotor = hwMap.dcMotor.get("left_front");
        backLeftMotor = hwMap.dcMotor.get("left_back");
        frontRightMotor = hwMap.dcMotor.get("right_front");
        backRightMotor = hwMap.dcMotor.get("right_back");

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intakeSubsystem = new Intake(hwMap.servo.get("intakeServo"));
        bucketSubsystem = new Bucket(hwMap.servo.get("bucketServo"));
        zlideSubsystem = new Zlide(hwMap.servo.get("zlideServo"));
        wristSubsystem = new Wrist(hwMap.servo.get("transferServo"));
        liftSubsystem = new Lift(hwMap.dcMotor.get("slideMotor"), hwMap.digitalChannel.get("liftLimitSwitch"));
        specimenGrabberSubsystem = new SpecimenGrabber(hwMap.servo.get("grabberServo"));
        drive = new SparkFunOTOSDrive(hardwareMap, new Pose2d(0,0,0));

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}