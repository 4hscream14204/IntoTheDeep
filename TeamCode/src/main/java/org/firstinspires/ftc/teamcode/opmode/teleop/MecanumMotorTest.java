package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.RobotBase;

@TeleOp
public class MecanumMotorTest extends OpMode {

    public RobotBase robotBase;
    public GamepadEx controller;

    public boolean bolFront = true;
    public boolean bolLeft = true;

    public String fBName;
    public String lRName;

    @Override
    public void init() {

        robotBase = new RobotBase(hardwareMap);

        controller = new GamepadEx(gamepad1);

        controller.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenActive(()-> CommandScheduler.getInstance().schedule(
                new InstantCommand(()-> bolFront = !bolFront)
        ));

        controller.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenActive(()-> CommandScheduler.getInstance().schedule(
                        new InstantCommand(()-> bolLeft = !bolLeft)
                ));

    }

    @Override
    public void loop() {

        controller.readButtons();

        if (bolLeft) {
            lRName = "left";
        } else {
            lRName = "right";
        }

        if (bolFront) {
            fBName = "_front";
        } else {
            fBName = "_back";
        }

        robotBase.chassisSubsystem.testWheels(controller.getLeftX(), controller.getLeftY(), controller.getRightX(), bolFront, bolLeft);

        telemetry.addData("Selected Motor", lRName + fBName);

        CommandScheduler.getInstance().run();
    }
}
