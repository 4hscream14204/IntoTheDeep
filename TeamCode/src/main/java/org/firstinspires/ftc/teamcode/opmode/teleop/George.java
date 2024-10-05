package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.commands.IntakeCommandGroup;
import org.firstinspires.ftc.teamcode.commands.IntakeTransferCommandGroup;
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

    Intake intakeSubsystem;
    Bucket bucketSubsystem;
    Zlide zlideSubsystem;
    Wrist wristSubsystem;
    Lift liftSubsystem;

    GamepadEx armController;

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();

        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        armController = new GamepadEx (gamepad2);

        intakeSubsystem = new Intake(hardwareMap.servo.get("intakeServo"));
        bucketSubsystem = new Bucket(hardwareMap.servo.get("bucketServo"));
        zlideSubsystem = new Zlide(hardwareMap.servo.get("zlideServo"));
        wristSubsystem = new Wrist(hardwareMap.servo.get("transferServo"));
        liftSubsystem = new Lift(hardwareMap.dcMotor.get("slideMotor"));

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        /*armController.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->bucketSubsystem.toggleBucket())
                        )); */

        armController.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(new IntakeCommandGroup(zlideSubsystem, wristSubsystem));

        armController.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(new IntakeTransferCommandGroup(zlideSubsystem, wristSubsystem));

    }

    @Override
    public void loop() {

        armController.readButtons();

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



        /*if (armController.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
            wristSubsystem.wristPickupPos();
        }
*/

        if (armController.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {
            wristSubsystem.wristTransferPos();
        }
        /*
        if(armController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) >= 0.1){
            intakeSubsystem.intakeSpeed((gamepad2.right_trigger));
        }
        if(armController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) >= 0.1){
            intakeSubsystem.intakeSpeed((gamepad2.left_trigger));
        }
        if (armController.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)) {
            bucketSubsystem.toggleBucket();
        }

         */
        intakeSubsystem.intakeSpeed( gamepad2.left_trigger/2 + -1 * gamepad2.right_trigger/2 + 0.5);

        /*if (armController.wasJustPressed(GamepadKeys.Button.DPAD_UP)){

            zlideSubsystem.zlideExtendPosition();

        }
*/
        /*if (armController.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)){

            zlideSubsystem.zlideBucketPosition();

        } */

        if (armController.wasJustPressed((GamepadKeys.Button.DPAD_RIGHT))){
            zlideSubsystem.zlideStartPosition();
        }

        /*if (gamepad2.right_stick_y>0.1){
            liftSubsystem.goDown(gamepad2.right_stick_y);
        }

        if (gamepad2.right_stick_y<-0.1){
            liftSubsystem.goUp(gamepad2.right_stick_y);
        }
/*
        if (gamepad2.right_trigger < 0.1 && gamepad2.left_trigger < 0.1) {
            liftSubsystem.stop();
        }

 */


        telemetry.addData ("lift motor", liftSubsystem.getPosition());

        telemetry.addData("right trigger value", gamepad2.right_trigger);

        telemetry.addData("left trigger value", gamepad2.left_trigger);

        CommandScheduler.getInstance().run();
    }
}