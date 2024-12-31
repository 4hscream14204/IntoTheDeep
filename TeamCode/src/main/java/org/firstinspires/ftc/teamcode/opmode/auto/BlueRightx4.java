package org.firstinspires.ftc.teamcode.opmode.auto;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.RobotBase;

public class BlueRightx4 extends OpMode {
    public TelemetryPacket telemetryPacket;

    public Pose2d startPose;

    public RobotBase robotBase;
    public GamepadEx armController;
    public GamepadEx baseController;
    public int waitSec = 0;
    public Action waitAction;
    public Action blueRightx4Action;


    @Override
    public void init() {
        startPose = new Pose2d(14, -61, Math.toRadians(0));
        robotBase =new RobotBase(hardwareMap);
        armController = new GamepadEx(gamepad2);
        baseController = new GamepadEx(gamepad1);
        CommandScheduler.getInstance().reset();
        robotBase.drive.pose = startPose;
        telemetryPacket = new TelemetryPacket();

        baseController.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new InstantCommand(
                        ()-> waitSec++
                ));

        baseController.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(new InstantCommand(
                        ()-> waitSec--
                ));


        blueRightx4Action = robotBase.drive.action(startpose)
                .splineToConstantHeading(new Vector2d(-5.56, 30.62), Math.toRadians(300.00))
                .splineToSplineHeading(new Pose2d(-33.15, 34.78, Math.toRadians(120.00)), Math.toRadians(200.00))
                .splineToLinearHeading(new Pose2d(-35.96, 40.26, Math.toRadians(50.00)), Math.toRadians(120.00))
                .splineToLinearHeading(new Pose2d(-41.45, 34.33, Math.toRadians(120.00)), Math.toRadians(200.00))
                .splineToLinearHeading(new Pose2d(-45.90, 41.01, Math.toRadians(50.00)), Math.toRadians(120.00))
                .splineToLinearHeading(new Pose2d(-50.20, 34.04, Math.toRadians(120.00)), Math.toRadians(200.00))
                .splineToLinearHeading(new Pose2d(-54.20, 39.52, Math.toRadians(20.00)), Math.toRadians(120.00))
                .splineToSplineHeading(new Pose2d(-49.01, 55.84, Math.toRadians(180.00)), Math.toRadians(87.06))
                .splineToConstantHeading(new Vector2d(-5.86, 29.14), Math.toRadians(270.00))
                .splineToConstantHeading(new Vector2d(-49.16, 56.28), Math.toRadians(150.00))
                .splineToConstantHeading(new Vector2d(-2.60, 28.70), Math.toRadians(270.00))
                .splineToConstantHeading(new Vector2d(-49.31, 55.98), Math.toRadians(150.00))
                .splineToConstantHeading(new Vector2d(0.07, 28.99), Math.toRadians(270.00))
                .splineToConstantHeading(new Vector2d(-62.81, 62.66), Math.toRadians(90.00))

                .build();

        robotBase.alliance = ITDEnums.EnmAlliance.BLUE;


    }

    @Override
    public void init_loop() {
        CommandScheduler.getInstance().run();
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
        CommandScheduler.getInstance().run();
        telemetry.addData("Wait time", 0);
        blueRightx4Action.run(telemetryPacket);
    }

    @Override
    public void stop() {
        robotBase.drive.updatePoseEstimate();
        DataStorage.alliance = robotBase.alliance;
        DataStorage.dblIMUFinalHeadingRad = robotBase.drive.otos.getPosition().h;

    }
}
