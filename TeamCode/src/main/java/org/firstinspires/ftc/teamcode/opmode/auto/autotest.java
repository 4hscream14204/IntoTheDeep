package org.firstinspires.ftc.teamcode.opmode.auto;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.base.ITDEnums;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.DataStorage;

@Autonomous(name = "crabtest")
public class autotest extends OpMode {

    public TelemetryPacket telemetryPacket;

    public Pose2d startPose;

    public RobotBase robotBase;
    public GamepadEx armController;
    public GamepadEx baseController;
    public int waitSec = 0;
    public Action waitAction;
    public  Action blueRightAction;

    @Override
    public void init() {

        startPose = new Pose2d(14, 61, Math.toRadians(0));
        robotBase =new RobotBase(hardwareMap);
        armController = new GamepadEx(gamepad2);
        baseController = new GamepadEx(gamepad1);
        CommandScheduler.getInstance().reset();
        telemetryPacket = new TelemetryPacket();

        robotBase.drive.pose = startPose;

        baseController.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new InstantCommand(
                        ()-> waitSec++
                ));

        baseController.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(new InstantCommand(
                        ()-> waitSec--
                ));

        blueRightAction = robotBase.drive.actionBuilder(startPose)
                .waitSeconds(2)
                .setTangent(Math.toRadians(270))
                //Go to sub
                .splineToConstantHeading(new Vector2d(10, 34), Math.toRadians(270), new TranslationalVelConstraint(20))
                .setTangent(Math.toRadians(90))
                //Goes to first sample
                .splineToLinearHeading(new Pose2d(29, 36,Math.toRadians(230)), Math.toRadians(310), new TranslationalVelConstraint(20))
                //.afterTime(new InstantCommand(()->robotBase.
                .setTangent(Math.toRadians(45))
                //goes to basket and drops off
                .splineToLinearHeading(new Pose2d(40, 57, Math.toRadians(135 )), Math.toRadians(45))
                //getting second sample
                .setTangent(270)
               .splineToLinearHeading(new Pose2d(40, 40, Math.toRadians(180)), Math.toRadians(270))
                // dropping off last sample
                .setTangent(Math.toRadians(45))
                .splineToLinearHeading(new Pose2d(44, 57, Math.toRadians(135)), Math.toRadians(45), new TranslationalVelConstraint(20))
                .setTangent(Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(18, 20, Math.toRadians(88)), Math.toRadians(180), new TranslationalVelConstraint(20))
              /*  .setTangent(Math.toRadians(270))
                // .splineTo(new Vector2d(67.55, 56.43), Math.toRadians(254.77))
                .splineToLinearHeading(new Pose2d(54, 31, Math.toRadians(180)), Math.toRadians(45), new TranslationalVelConstraint(20))
                .setTangent(Math.toRadians(45))
                .splineToLinearHeading(new Pose2d(57, 50, Math.toRadians(320)), Math.toRadians(45), new TranslationalVelConstraint(20))
                .setTangent(Math.toRadians(270))
                .splineTo(new Vector2d(50, 19), Math.toRadians(220), new TranslationalVelConstraint(20))
             */   //.splineTo(new Vector2d(24, 12), Math.toRadians(180), new TranslationalVelConstraint(20))
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
        blueRightAction.run(telemetryPacket);
        robotBase.drive.updatePoseEstimate();
//If you are running trajectories you can check if a trajectory is running and if it is not then do a pose update

//Roadrunner provides subclasses with the pose data
//Poses have the x and y coordinates and heading, heading is in radians not degrees
        telemetry.addData("x", robotBase.drive.pose.position.x);
        telemetry.addData("y", robotBase.drive.pose.position.y);
        telemetry.addData("heading (deg)", Math.toDegrees(robotBase.drive.pose.heading.toDouble()));
    }

    @Override
    public void stop() {
        robotBase.drive.updatePoseEstimate();
        DataStorage.alliance = robotBase.alliance;
        DataStorage.dblIMUFinalHeadingRad = robotBase.drive.otos.getPosition().h;
    }
}