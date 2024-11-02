package org.firstinspires.ftc.teamcode.opmode.auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.base.RobotBase;


@Autonomous(name = "Blue right hang and park")
public class AutoBlueRightHangAndPark extends OpMode {

    public Pose2d startPose;

    public RobotBase robotBase;
    public GamepadEx armController;
    public GamepadEx baseController;
    public int waitSec = 0;
    public Action waitAction;

    @Override
    public void init() {

        robotBase =new RobotBase(hardwareMap);
        armController = new GamepadEx(gamepad2);
        baseController = new GamepadEx(gamepad1);
        CommandScheduler.getInstance().reset();
        startPose = new Pose2d(0, 0, Math.toRadians(0));

        baseController.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new InstantCommand(
                        ()-> waitSec++
                ));

        baseController.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(new InstantCommand(
                        ()-> waitSec--
                ));
    }

    @Override
    public void init_loop() {
        telemetry.addData("Wait time", waitSec);
    }

    @Override
    public void start() {
        if (waitSec > 0) {
            waitAction = robotBase.drive.actionBuilder(startPose)
                    .waitSeconds(waitSec)
                    .build();

            Actions.runBlocking(waitAction);
        }
    }

    @Override
    public void loop() {


    }

    @Override
    public void stop() {


    }

}
