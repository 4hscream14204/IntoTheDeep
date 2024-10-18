package org.firstinspires.ftc.teamcode.opmode.teleop;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.IntakeCommandGroup;
import org.firstinspires.ftc.teamcode.commands.IntakeTransferCommandGroup;
import org.firstinspires.ftc.teamcode.commands.LiftHome;
import org.firstinspires.ftc.teamcode.commands.Liftbucketcommandgroup;
import org.firstinspires.ftc.teamcode.subsystems.Bucket;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.firstinspires.ftc.teamcode.subsystems.Zlide;

@TeleOp(name="George")
public class TeleGeorge extends OpMode{
    RobotBase robotBase;
    boolean fieldCentric  = true;
    double denominator;
    double frontLeftPower;
    double backLeftPower;
    double frontRightPower;
    double backRightPower;
    public GamepadEx armController;
    public GamepadEx baseController;

    @Override
    public void init() {
        robotBase =new RobotBase(hardwareMap);
        armController = new GamepadEx(gamepad2);
        baseController = new GamepadEx(gamepad1);
        CommandScheduler.getInstance().reset();

        armController.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(new IntakeCommandGroup(robotBase.zlideSubsystem,robotBase.wristSubsystem));

        armController.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(new IntakeTransferCommandGroup(robotBase.zlideSubsystem, robotBase.wristSubsystem, robotBase.bucketSubsystem));

        armController.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new LiftHome(robotBase.liftSubsystem));

        baseController.getGamepadButton((GamepadKeys.Button.Y))
                .whenPressed(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->fieldCentric = !fieldCentric)
                ));

        armController.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(new Liftbucketcommandgroup(robotBase.liftSubsystem,robotBase.bucketSubsystem));

        armController.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.HIGHCHAMBER))
                ));;

        baseController.getGamepadButton(GamepadKeys.Button.START)
                .whenPressed(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand((()->robotBase.imu.resetYaw()))
                ));

    }

    @Override
    public void loop() {
        double botHeading = robotBase.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        armController.readButtons();
        baseController.readButtons();


        double y = -gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y); // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x * Math.abs(gamepad1.left_stick_x);
        double rx = gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x);

        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        if (fieldCentric) {
            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            frontLeftPower = (rotY + rotX + rx) / denominator;
            backLeftPower = (rotY - rotX + rx) / denominator;
            frontRightPower = (rotY - rotX - rx) / denominator;
            backRightPower = (rotY + rotX - rx) / denominator;
        } else {
            denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            frontLeftPower = (y + x + rx) / denominator;
            backLeftPower = (y - x + rx) / denominator;
            frontRightPower = (y - x - rx) / denominator;
            backRightPower = (y + x - rx) / denominator;
        }

        robotBase.frontLeftMotor.setPower(frontLeftPower);
        robotBase.backLeftMotor.setPower(backLeftPower);
        robotBase.frontRightMotor.setPower(frontRightPower);
        robotBase.backRightMotor.setPower(backRightPower);
        telemetry.addData("Left Stick y", y);
        telemetry.addData("Left Stick x", x);
        telemetry.addData("Right Stick rx", rx);

        if (gamepad1.options) {
            robotBase.imu.resetYaw();
        }

        if (armController.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
            robotBase.wristSubsystem.wristPickupPos();
        }

        if (armController.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {
            robotBase.wristSubsystem.wristTransferPos();
        }

        if (armController.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)) {
            robotBase.bucketSubsystem.toggleBucket();
        }

        robotBase.intakeSubsystem.intakeSpeed(gamepad1.left_trigger / 2 + -1 * gamepad1.right_trigger / 2 + 0.5);

            if(armController.wasJustPressed((GamepadKeys.Button.DPAD_RIGHT))){
                robotBase.zlideSubsystem.zlideStartPosition();
            }

           telemetry.addData("lift motor", robotBase.liftSubsystem.getPosition());

            telemetry.addData("right trigger value", gamepad2.right_trigger);

            telemetry.addData("left trigger value", gamepad2.left_trigger);

            telemetry.addData("Lift motor power", robotBase.liftSubsystem.getPower());

            telemetry.addData("feild centric", fieldCentric);

            telemetry.addData("Gyro", robotBase.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));

            CommandScheduler.getInstance().run();

    }
}