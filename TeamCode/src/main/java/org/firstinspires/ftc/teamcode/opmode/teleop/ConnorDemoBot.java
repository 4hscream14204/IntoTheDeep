package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.RobotBase;

@TeleOp(name = ("Connor Bot"))
public class ConnorDemoBot extends OpMode {

    public RobotBase robotBase;
    double dubFrontRightPower;
    double dubFrontLeftPower;
    double dubBackRightPower;
    double dubBackLeftPower;
    public GamepadEx chassisController;

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        robotBase = new RobotBase(hardwareMap);

        chassisController = new GamepadEx(gamepad1);

        chassisController.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(()-> CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.clawsubsystem.togglePosition())
                ));

        new Trigger(()->chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1)
                .or(new Trigger(()->chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1))
                .whileActiveContinuous(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.armsubsystem.moveArm(robotBase, chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) - chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER))
                        )))
                .whenInactive(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.armsubsystem.stopInPlace())
                ));
    }

    @Override
    public void loop() {
        chassisController.readButtons();
        robotBase.chassisSubsystem.drive(chassisController.getLeftX(), chassisController.getLeftY(), chassisController.getRightX());

        telemetry.addData("Arm AMPs", robotBase.armsubsystem.getAmps());
        telemetry.addData("Max AMPs", robotBase.armsubsystem.dblAmpLimit);
        telemetry.addData("Is stalling", robotBase.armsubsystem.isStalling());
    }
}