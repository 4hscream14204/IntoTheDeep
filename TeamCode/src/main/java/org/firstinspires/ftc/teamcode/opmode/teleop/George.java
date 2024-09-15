package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.Bucket;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.firstinspires.ftc.teamcode.subsystems.Zlide;

@TeleOp(name="run George")
public class George extends OpMode{

    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;
    DcMotor slideMotor;
    Intake intakeSubsystem;
    Bucket bucketSubsystem;
    Zlide zlideSubsystem;
    Wrist wristSubsystem;
    Lift liftSubsystem;

    @Override
    public void init() {

        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        intakeSubsystem = new Intake(hardwareMap.servo.get("intakeServo"));
        bucketSubsystem = new Bucket(hardwareMap.servo.get("bucketServo"));
        zlideSubsystem = new Zlide(hardwareMap.servo.get("zlideServo"));
        wristSubsystem = new Wrist(hardwareMap.servo.get("transferServo"));
        liftSubsystem = new Lift(hardwareMap.dcMotor.get("slideMotor"));

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);


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



        if (gamepad2.left_bumper) {
            wristSubsystem.wristPickupPos();
        }


        if (gamepad2.right_bumper) {
            wristSubsystem.wristTransferPos();
        }
        intakeSubsystem.intakeSpeed((gamepad2.right_stick_y+1)/2);

        if (gamepad2.x) {

            bucketSubsystem.toggleBucket();
        }

        if (gamepad2.dpad_up){

            zlideSubsystem.zlideExtendPosition();

        }

        if (gamepad2.dpad_down){

            zlideSubsystem.zlideBucketPosition();

        }

        if (gamepad2.left_trigger>0.1){
            liftSubsystem.goDown(gamepad2.left_trigger);
        }

        if (gamepad2.right_trigger>0.1){
            liftSubsystem.goUp(gamepad2.right_trigger);
        }
    }
}