package org.firstinspires.ftc.teamcode.base;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.subsystems.Bucket;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
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


    public GamepadEx armController;
    public GamepadEx baseController;


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

        frontLeftMotor = hwMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hwMap.dcMotor.get("backLeftMotor");
        frontRightMotor = hwMap.dcMotor.get("frontRightMotor");
        backRightMotor = hwMap.dcMotor.get("backRightMotor");
        armController = new GamepadEx(gamepad2);
        baseController = new GamepadEx(gamepad1);

        intakeSubsystem = new Intake(hwMap.servo.get("intakeServo"));
        bucketSubsystem = new Bucket(hwMap.servo.get("bucketServo"));
        zlideSubsystem = new Zlide(hwMap.servo.get("zlideServo"));
        wristSubsystem = new Wrist(hwMap.servo.get("transferServo"));
        liftSubsystem = new Lift(hwMap.dcMotor.get("slideMotor"));

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}