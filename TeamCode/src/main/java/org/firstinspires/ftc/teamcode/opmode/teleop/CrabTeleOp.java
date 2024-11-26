package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.base.RobotBase;

@TeleOp(name = ("Crab TeleOp"))
public class CrabTeleOp extends OpMode {
    RobotBase robotBase;
    boolean bolFieldCentric = true;
    double dubFrontRightPower;
    double dubFrontLeftPower;
    double dubBackRightPower;
    double dubBackLeftPower;
    double dubDenominator;
    public GamepadEx armController;
    public GamepadEx chassisController;

    @Override
    public void init() {
        robotBase = new RobotBase(hardwareMap);
        chassisController = new GamepadEx(gamepad1);
        armController = new GamepadEx(gamepad2);

        chassisController.getGamepadButton(GamepadKeys.Button.START)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> robotBase.drive.otos.setPosition(new SparkFunOTOS.Pose2D(0, 0, Math.toRadians(0))))
                ));
        chassisController.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> bolFieldCentric = !bolFieldCentric)
                ));
       /* armController.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.HOME))
                ));
        armController.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.HOME))
                ));
        armController.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP))
                ));
        armController.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.DROPOFF))
                ));
        armController.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(()-> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME))
                ));
        armController.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() ->robotBase.clawSubsystem.ToggleClaw())
                ));
        armController.getGamepadButton(GamepadKeys.Button.BACK)
                .whenPressed(new InstantCommand(() -> CommandScheduler.getInstance().cancelAll()));*/
    }

    public void loop(){
        telemetry.update();
        chassisController.readButtons();
        armController.readButtons();
        gamepad1.setLedColor(0, 1, 0, 120000);
        gamepad2.setLedColor(0, 1, 0, 120000);
        double botHeading = robotBase.drive.otos.getPosition().h;
        ElapsedTime timer = new ElapsedTime();
        int dblCurrentTime = (int) timer.seconds();

        double chassisLeftStickY = chassisController.getLeftY() * Math.abs(chassisController.getLeftY());
        double chassisLeftStickX = chassisController.getLeftX() * Math.abs(chassisController.getLeftX());
        double chassisRightStickX = chassisController.getRightX() * Math.abs(chassisController.getRightX());
        double rotX = chassisLeftStickX * Math.cos(-botHeading) - chassisLeftStickY * Math.sin(-botHeading);
        double rotY = chassisLeftStickX * Math.sin(-botHeading) + chassisLeftStickY * Math.cos(-botHeading);

        if (bolFieldCentric) {
            dubDenominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(chassisRightStickX), 1);
            dubFrontLeftPower = (rotY + rotX + chassisRightStickX) / dubDenominator;
            dubBackLeftPower = (rotY - rotX + chassisRightStickX) / dubDenominator;
            dubFrontRightPower = (rotY - rotX - chassisRightStickX) / dubDenominator;
            dubBackRightPower = (rotY + rotX - chassisRightStickX) / dubDenominator;
        } else {
            dubDenominator = Math.max(Math.abs(chassisLeftStickX) + Math.abs(chassisLeftStickX) + Math.abs(chassisRightStickX), 1);
            dubFrontLeftPower = (chassisLeftStickY + chassisLeftStickX + chassisRightStickX) / dubDenominator;
            dubBackLeftPower = (chassisLeftStickY - chassisLeftStickX + chassisRightStickX) / dubDenominator;
            dubFrontRightPower = (chassisLeftStickY - chassisLeftStickX - chassisRightStickX) / dubDenominator;
            dubBackRightPower = (chassisLeftStickY + chassisLeftStickX - chassisRightStickX) / dubDenominator;
        }
        robotBase.frontLeftMotor.setPower(dubFrontLeftPower);
        robotBase.backLeftMotor.setPower(dubBackLeftPower);
        robotBase.frontRightMotor.setPower(dubFrontRightPower);
        robotBase.backRightMotor.setPower(dubBackRightPower);

        //robotBase.intakeSubsystem.intakeSpeed(chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) / 2 + -1 * chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) / 2 + 0.5);

        if(armController.getRightY() > 0.1){
            robotBase.shoulderSubsystem.goUp(armController.getRightY());
        }

        if(armController.getRightY() < -0.1){
            robotBase.shoulderSubsystem.goDown(armController.getRightY());
        }

        if(armController.getRightY() < 0.1 && armController.getRightY() > -0.1){
            robotBase.shoulderSubsystem.stopInPlace();
        }

        if(gamepad2.right_trigger > 0.1){
            robotBase.extensionSubsystem.extendForward(gamepad2.right_trigger);
        }

        if(gamepad2.left_trigger > 0.1){
            robotBase.extensionSubsystem.extendBack(gamepad2.left_trigger);
        }

        if(gamepad2.left_trigger < 0.1 && gamepad2.right_trigger < 0.1){
            robotBase.extensionSubsystem.stopInPlace();
        }
        //Connor: I don't think we need these but I commented them just in case.
        /*telemetry.addData("Chassis Left Stick Y", chassisLeftStickY);
        telemetry.addData("Chassis Left Stick X", chassisLeftStickX);
        telemetry.addData("Chassis Right Stick X", chassisRightStickX);
        */

       /* if(robotBase.timerSubsystem.hasEndgamePassed(dblCurrentTime)){
            gamepad1.rumble(0.25, 0.25, 500);
            gamepad2.rumble(0.25, 0.25, 500);
        };
        */

        telemetry.addData("Shoulder Position", robotBase.shoulderSubsystem.shoulderGetPosition());
        telemetry.addData("Shoulder Power" , robotBase.shoulderSubsystem.getPower());
        telemetry.addData("Extension Position", robotBase.extensionSubsystem.extensionGetPosition());
        telemetry.addData("Extension Power", robotBase.extensionSubsystem.getPower());
        //telemetry.addData("Color Sensor", robotBase.intakeSubsystem.checkSampleColor());
        telemetry.addData("FieldCentric", bolFieldCentric);
        telemetry.addData("Gyro", Math.toDegrees(robotBase.drive.otos.getPosition().h));
        telemetry.addData("Arm Right Trigger", gamepad2.right_trigger);
        telemetry.addData("Arm Left Trigger", gamepad2.left_trigger);
        //telemetry.addData("Shoulder Limit Switch", robotBase.shoulderSubsystem.isShoulderHome());
        //telemetry.addData("Extension Limit Switch", robotBase.extensionSubsystem.isExtensionHome());

        CommandScheduler.getInstance().run();

    }
}
