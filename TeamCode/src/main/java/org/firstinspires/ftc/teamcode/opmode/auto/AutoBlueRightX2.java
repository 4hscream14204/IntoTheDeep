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
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.base.ITDEnums;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.DataStorage;
import org.firstinspires.ftc.teamcode.subsystems.Lift;


@Autonomous(name = "Blue Right pickup two")
public class AutoBlueRightX2 extends OpMode {

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

        startPose = new Pose2d(-14, 61, Math.toRadians(180));
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

        blueRightAction = robotBase.drive.actionBuilder(startPose)
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberClosed())))
                .afterTime(0.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.AUTOHIGHCHAMBERSTART))))
                .waitSeconds(0.25)
                .setTangent(Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(-13, 45), Math.toRadians(-90.00), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-13, 32), Math.toRadians(-90.00), new TranslationalVelConstraint(20))
                .afterTime(0.25, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.AUTOHIGHCHAMBERCLAMP))))
                .afterTime(2, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberOpen())))
                .waitSeconds(2)
                .setTangent(Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(-40, 67, Math.toRadians(90)), Math.toRadians(180.00))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.PICKUP))))
                .splineToConstantHeading(new Vector2d(-55.5, 67), Math.toRadians(180.00), new TranslationalVelConstraint(20))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberClosed())))
                .afterTime(0.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.HIGHCHAMBERSTART))))
                .waitSeconds(1)
                .setTangent(0)
                .splineToSplineHeading(new Pose2d(-10, 40, Math.toRadians(180)), Math.toRadians(-90.00), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-10, 33.5), Math.toRadians(-90.00), new TranslationalVelConstraint(20))
                .afterTime(0.25, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.HIGHCHAMBERCLAMP))))
                .afterTime(2, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberOpen())))
                .waitSeconds(1)
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-56, 59), Math.toRadians(180.00), new TranslationalVelConstraint(20))
                 .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.HOME))))
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
    }

    @Override
    public void stop() {
        robotBase.drive.updatePoseEstimate();
        DataStorage.alliance = robotBase.alliance;
        DataStorage.dblIMUFinalHeadingRad = robotBase.drive.otos.getPosition().h;
    }
}