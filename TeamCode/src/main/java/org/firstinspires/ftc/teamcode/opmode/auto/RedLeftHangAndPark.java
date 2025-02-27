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
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.ITDCrabEnums;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.SpecimenPickupAutoCommandGroup;
import org.firstinspires.ftc.teamcode.commands.HangSpecimenAutoCommandGroup;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
@Disabled
@Autonomous(name = "RedLeftHangAndPArk")
public class RedLeftHangAndPark extends OpMode {
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
        startPose = new Pose2d(14, 61, Math.toRadians(0));
        robotBase =new RobotBase(hardwareMap);
        armController = new GamepadEx(gamepad2);
        baseController = new GamepadEx(gamepad1);
        CommandScheduler.getInstance().reset();
        robotBase.drive.pose = startPose;
        telemetryPacket = new TelemetryPacket();
        robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.INIT);
        robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.HOME);
        robotBase.elbowSubsystem.enmElbowPosition = Elbow.ElbowPosition.HOME;
        robotBase.clawSubsystem.closeClaw();

        baseController.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new InstantCommand(
                        ()-> waitSec++
                ));

        baseController.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(new InstantCommand(
                        ()-> waitSec--
                ));


        blueRightx4Action = robotBase.drive.actionBuilder(startPose)
                .setTangent(Math.toRadians(270))
                .afterTime(0.39, ()-> CommandScheduler.getInstance().schedule(new SpecimenPickupAutoCommandGroup(robotBase)))
                .splineToConstantHeading(new Vector2d(1.0, 35.00), Math.toRadians(270.00), new TranslationalVelConstraint(20))
                .waitSeconds(0.2)
                .splineToConstantHeading(new Vector2d(1.0, 23.00), Math.toRadians(270.00), new TranslationalVelConstraint(20))
                .waitSeconds(0.3)
                .afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroup(robotBase)))
                .waitSeconds(0.5)
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(35, 34, Math.toRadians(270)), Math.toRadians(0), new TranslationalVelConstraint(30))
                //.waitSeconds(0.5)
                .afterTime(0, ()-> CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.clawSubsystem.closeClaw())))
                .afterTime(0, ()-> CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.LOWBUCKET))))
                .splineToConstantHeading(new Vector2d(35, 18), Math.toRadians(270), new TranslationalVelConstraint(30))
                .afterTime(0, ()-> CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP))))
                .splineToConstantHeading(new Vector2d(30, 10), Math.toRadians(180), new TranslationalVelConstraint(30))
                .splineToConstantHeading(new Vector2d(20, 9.5), Math.toRadians(180), new TranslationalVelConstraint(20))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOPARK))))
                .build();


        robotBase.alliance = ITDCrabEnums.EnmAlliance.RED;


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
        robotBase.drive.updatePoseEstimate();
        telemetry.addData("x", robotBase.drive.pose.position.x);
        telemetry.addData("y", robotBase.drive.pose.position.y);
        telemetry.addData("heading (deg)", Math.toDegrees(robotBase.drive.pose.heading.toDouble()));
        //  telemetry.addData("Feild Position y")
        telemetry.addData("Shoulder Position", robotBase.shoulderSubsystem.shoulderGetPosition());
        blueRightx4Action.run(telemetryPacket);
    }

    @Override
    public void stop() {
        robotBase.drive.updatePoseEstimate();
        //DataStorage.alliance = robotBase.alliance;
        DataStorage.dblIMUFinalHeadingRad = robotBase.drive.otos.getPosition().h;

    }
}

