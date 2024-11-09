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

@Autonomous(name = "BlueLeftX2")
public class AutoBlueLeftX2 extends OpMode {public TelemetryPacket telemetryPacket;

    public Pose2d startPose;

    public RobotBase robotBase;
    public GamepadEx armController;
    public GamepadEx baseController;
    public int waitSec = 0;
    public Action waitAction;
    public  Action blueLeftAction;

    @Override
    public void init() {

        startPose = new Pose2d(14, 61, Math.toRadians(180));
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

        blueLeftAction = robotBase.drive.actionBuilder(startPose)
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberClosed())))
                .afterTime(0.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.AUTOHIGHCHAMBERSTART))))
                .waitSeconds(0.25)
                .setTangent(Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(13, 45), Math.toRadians(-90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(13,32),Math.toRadians(-90),new TranslationalVelConstraint(20))
                .afterTime(0.25, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.AUTOHIGHCHAMBERCLAMP))))
                .afterTime(2, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberOpen())))
                .waitSeconds(1)
                .setTangent(Math.toRadians(90))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.HOME))))
                .splineToConstantHeading(new Vector2d(38,32),Math.toRadians(-90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(35, 14), Math.toRadians(-90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(46,10),Math.toRadians(90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(53, 61), Math.toRadians(45.00),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(40.27, 15.78), Math.toRadians(-90.00),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(58,10),Math.toRadians(90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(58, 61), Math.toRadians(45),new TranslationalVelConstraint(20))
                .afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.FIRSTLEVELASCENT))))
                .afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberClosed())))
                .splineToConstantHeading(new Vector2d(39, 14), Math.toRadians(-90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(25, 12), Math.toRadians(180),new TranslationalVelConstraint(20))
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
        blueLeftAction.run(telemetryPacket);
    }

    @Override
    public void stop() {
        robotBase.drive.updatePoseEstimate();
        DataStorage.alliance = robotBase.alliance;
        DataStorage.dblIMUFinalHeadingRad = robotBase.drive.otos.getPosition().h;
    }
}
