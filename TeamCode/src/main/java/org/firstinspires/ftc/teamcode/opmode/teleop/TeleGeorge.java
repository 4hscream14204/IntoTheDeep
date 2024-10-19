package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.IntakeCommandGroup;
import org.firstinspires.ftc.teamcode.commands.IntakeTransferCommandGroup;
import org.firstinspires.ftc.teamcode.commands.LiftBucketCommandGroup;
import org.firstinspires.ftc.teamcode.commands.LiftHome;
import org.firstinspires.ftc.teamcode.commands.LiftBucketCommandGroup;
import org.firstinspires.ftc.teamcode.subsystems.Lift;

@TeleOp(name="George")
public class TeleGeorge extends OpMode{
    RobotBase robotBase;
    boolean bolFieldCentric = true;
    double dubDenominator;
    double dubFrontLeftPower;
    double dubBackLeftPower;
    double dubFrontRightPower;
    double dubBackRightPower;
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
                        new InstantCommand(()-> bolFieldCentric = !bolFieldCentric)
                ));

        armController.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(new LiftBucketCommandGroup(robotBase.liftSubsystem,robotBase.bucketSubsystem));

        armController.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.HIGHCHAMBER))
                ));;

        baseController.getGamepadButton(GamepadKeys.Button.START)
                .whenPressed(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand((()->robotBase.imu.resetYaw()))
                ));

        armController.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.wristSubsystem.wristPickupPos())
                ));

        armController.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.wristSubsystem.wristTransferPos())
                ));

        armController.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.bucketSubsystem.toggleBucket())
                ));

        armController.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.zlideSubsystem.zlideStartPosition())
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

        if (bolFieldCentric) {
            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            dubDenominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            dubFrontLeftPower = (rotY + rotX + rx) / dubDenominator;
            dubBackLeftPower = (rotY - rotX + rx) / dubDenominator;
            dubFrontRightPower = (rotY - rotX - rx) / dubDenominator;
            dubBackRightPower = (rotY + rotX - rx) / dubDenominator;
        } else {
            dubDenominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            dubFrontLeftPower = (y + x + rx) / dubDenominator;
            dubBackLeftPower = (y - x + rx) / dubDenominator;
            dubFrontRightPower = (y - x - rx) / dubDenominator;
            dubBackRightPower = (y + x - rx) / dubDenominator;
        }

        robotBase.frontLeftMotor.setPower(dubFrontLeftPower);
        robotBase.backLeftMotor.setPower(dubBackLeftPower);
        robotBase.frontRightMotor.setPower(dubFrontRightPower);
        robotBase.backRightMotor.setPower(dubBackRightPower);
        telemetry.addData("Left Stick y", y);
        telemetry.addData("Left Stick x", x);
        telemetry.addData("Right Stick rx", rx);

        robotBase.intakeSubsystem.intakeSpeed(gamepad1.left_trigger / 2 + -1 * gamepad1.right_trigger / 2 + 0.5);

        telemetry.addData("lift motor", robotBase.liftSubsystem.getPosition());

        telemetry.addData("right trigger value", gamepad2.right_trigger);

        telemetry.addData("left trigger value", gamepad2.left_trigger);

        telemetry.addData("Lift motor power", robotBase.liftSubsystem.getPower());

        telemetry.addData("field centric", bolFieldCentric);

        telemetry.addData("Gyro", robotBase.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));

        telemetry.addData("Lift Limit Switch: ", robotBase.liftSubsystem.getSwitchState());

            CommandScheduler.getInstance().run();

    }
}