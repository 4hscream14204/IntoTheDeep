package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.roadrunner.ftc.SparkFunOTOSCorrected;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.roadrunner.SparkFunOTOSDrive;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;

@TeleOp(name = ("Crab TeleOp"))
public class CrabTeleOp extends OpMode {
    RobotBase robotBase;
    boolean bolFieldCentric;
    double dubFrontRightPower;
    double dubFrontLeftPower;
    double dubBackRightPower;
    double dubBackLeftPower;
    double dubDenominator;
    public GamepadEx armController;
    public GamepadEx chassisController;

    @Override
    public void init(){
        robotBase = new RobotBase(hardwareMap);
        chassisController = new GamepadEx(gamepad1);
        armController = new GamepadEx(gamepad2);

        chassisController.getGamepadButton(GamepadKeys.Button.START)
                .whenPressed(()-> CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.drive.otos.setPosition(new SparkFunOTOS.Pose2D(0, 0, Math.toRadians(0))))
                ));
        chassisController.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(()-> CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->bolFieldCentric = !bolFieldCentric)
                ));
    }

    public void loop(){
        telemetry.update();
        chassisController.readButtons();
        armController.readButtons();
        double botHeading = robotBase.drive.otos.getPosition().h;

        double chassisLeftStickY = -chassisController.getLeftY() * Math.abs(chassisController.getLeftY());
        double chassisLeftStickX = chassisController.getLeftX() * Math.abs(chassisController.getLeftX());
        double chassisRightStickX = chassisController.getRightX() * Math.abs(chassisController.getRightX());
        double rotX = chassisLeftStickX * Math.cos(-botHeading) - chassisLeftStickY * Math.sin(-botHeading);
        double rotY = chassisLeftStickX * Math.sin(-botHeading) + chassisLeftStickY * Math.cos(-botHeading);

        if (bolFieldCentric) {
            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
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

        robotBase.intakeSubsystem.intakeSpeed(chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) / 2 + -1 * chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) / 2 + 0.5);

        if(armController.getRightY() > 0.01){
            robotBase.shoulderSubsystem.goUp(armController.getRightY());
        }

        if(armController.getRightY() < -0.01){
            robotBase.shoulderSubsystem.goDown(armController.getRightY());
        }

        telemetry.addData("Left Stick Y", chassisLeftStickY);
        telemetry.addData("Left Stick X", chassisLeftStickX);
        telemetry.addData("Right Stick X", chassisRightStickX);
        telemetry.addData("Shoulder Position", robotBase.shoulderSubsystem.shoulderGetPosition());
    }
}
