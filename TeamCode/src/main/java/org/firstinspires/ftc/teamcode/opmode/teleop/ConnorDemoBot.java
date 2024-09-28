package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Connor demo bot")
public class ConnorDemoBot extends OpMode {

    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;
    Servo claw;
    DcMotorSimple arm = null;

    @Override
    public void init() {

        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        claw = hardwareMap.servo.get("clawServo");
        arm = hardwareMap.get(DcMotorSimple.class, "armMotor");


        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        arm.setDirection(DcMotorSimple.Direction.FORWARD);
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

        if(gamepad1.a){
            claw.setPosition(0.6111);
        }

        if(gamepad1.b){
            claw.setPosition(0.80555);
        }

        if(gamepad1.right_trigger > 0.1){
            arm.setPower(-0.3);
        }

        if(gamepad1.left_trigger > 0.1){
            arm.setPower(0.3);
        }

        if(gamepad1.left_trigger < 0.1 && gamepad1.right_trigger < 0.1){
            arm.setPower(0);
        }

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);
        telemetry.addData("Left Stick y", y);
        telemetry.addData("Left Stick x", x);
        telemetry.addData("Right Stick x", rx);
        telemetry.addData("Overall speed", x+y);
        telemetry.addData("Claw Position: ", claw.getPosition());
    }
}
