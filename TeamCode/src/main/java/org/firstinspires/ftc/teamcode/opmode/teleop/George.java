package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.Bucket;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

@TeleOp(name="run George")
public class George extends OpMode{

    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;
    DcMotor slideMotor;
    Servo bucketServo;
    Intake intakeSubsystem;
    Bucket bucketSubsystem;

    @Override
    public void init() {

        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        slideMotor = hardwareMap.dcMotor.get("slideMotor");
        bucketServo = hardwareMap.servo.get("bucketServo");

        intakeSubsystem = new Intake(hardwareMap.servo.get("intakeServo"));

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        bucketServo.setPosition(1.0);

        double slidePosition = slideMotor.getCurrentPosition();
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setTargetPosition(0);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(0);
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        double y = -gamepad1.left_stick_y  * Math.abs (gamepad1.left_stick_y); // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x * Math.abs (gamepad1.left_stick_x);
        double rx = gamepad1.right_stick_x * Math.abs (gamepad1.right_stick_x);


        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);
        telemetry.addData("Left Stick y", y);
        telemetry.addData("Left Stick x", x);
        telemetry.addData("Right Stick rx", rx);
        if (gamepad1.a) {
            bucketServo.setPosition(0.722);
        }

        if (gamepad1.x) {
            bucketServo.setPosition(1.0);
        }

        double slidePosition = slideMotor.getCurrentPosition();
        telemetry.addData("Slide position", slidePosition);

        if (gamepad1.left_bumper) {
            slideMotor.setPower(0.4);
            slideMotor.setTargetPosition(0);
        }

        if(slidePosition > -15){
            slideMotor.setPower(0);
        }

        if (gamepad1.right_bumper) {
            slideMotor.setPower(-0.5);
            slideMotor.setTargetPosition(-810);
        }
        intakeSubsystem.intakeSpeed(gamepad2.right_stick_y);

        if (gamepad2.x) {

            bucketSubsystem.toggleBucket();
        }
    }
}