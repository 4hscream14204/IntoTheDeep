package org.firstinspires.ftc.teamcode.opmode.auto;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.teamcode.base.ITDEnums;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.roadrunner.SparkFunOTOSDrive;

@Autonomous(name = "RoutTest")
public class RouteTesting extends OpMode {
    public TelemetryPacket telemetryPacket;
    public Pose2d startPose;
    public RobotBase robotBase;
    public GamepadEx armController;
    public GamepadEx baseController;
    public int waitSec = 0;
    public Action waitAction;
    private Action routTest;
    SparkFunOTOSDrive drive;

    @Override
    public void init (){
        Pose2d beginPose = new Pose2d(14, 61, Math.toRadians(180));

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
        routTest = drive.actionBuilder(beginPose)
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberClosed())))
                .afterTime(0.5, ()-> CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.AUTOHIGHCHAMBERSTART))))
                .waitSeconds(0.25)
                .setTangent(Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(3, 45), Math.toRadians(-90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(3,33.25),Math.toRadians(-90),new TranslationalVelConstraint(20))
                .afterTime(0.25, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.AUTOHIGHCHAMBERCLAMP))))
                .afterTime(1.75, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberOpen())))
                .waitSeconds(1)
                .setTangent(Math.toRadians(90))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.HOME))))
                .splineToSplineHeading(new Pose2d(37, 25, Math.toRadians(90)), Math.toRadians(-90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(37, 6), Math.toRadians(-90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(46,10),Math.toRadians(90),new TranslationalVelConstraint(20))
                .splineToSplineHeading(new Pose2d(53, 55, Math.toRadians(45.00)), Math.toRadians(225),new TranslationalVelConstraint(20))
                .splineToLinearHeading(new Pose2d(57, 15, Math.toRadians(90.00)), Math.toRadians(45),new TranslationalVelConstraint(20))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(54,55, Math.toRadians(45)), Math.toRadians(45),new TranslationalVelConstraint(20))
                .splineToSplineHeading(new Pose2d(46, 12, Math.toRadians(45.00)), Math.toRadians(90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(58,59), Math.toRadians(45),new TranslationalVelConstraint(20))
                .afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.FIRSTLEVELASCENT))))
                .afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberClosed())))
                .splineToSplineHeading(new Pose2d(39, 14, Math.toRadians(90)), Math.toRadians(45),new TranslationalVelConstraint(20))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(25, 12,Math.toRadians(90)), Math.toRadians(180),new TranslationalVelConstraint(20))
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
    public void loop (){
        CommandScheduler.getInstance().run();
        telemetry.addData("Wait time", 0);
        routTest.run(telemetryPacket);
    }
}
