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

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.AutoBlueLeftHangSpecimenCommandGroup;
import org.firstinspires.ftc.teamcode.commands.AutoSamplePickUpCommandGroup;
import org.firstinspires.ftc.teamcode.commands.BucketEjectAndHomeCommandGroup;
import org.firstinspires.ftc.teamcode.commands.BucketExtendUpCommandGroup;
import org.firstinspires.ftc.teamcode.commands.EjectCommandGroup;
import org.firstinspires.ftc.teamcode.commands.HangSpecimenAutoCommandGroupPartOne;
import org.firstinspires.ftc.teamcode.commands.HangSpecimenAutoCommandGroupPartTwo;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

@Autonomous (name = "BlueLeft4x")
public class BlueLefttx4 extends OpMode {
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
                .afterTime(0.39, ()-> CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroupPartOne(robotBase)))
                .splineToConstantHeading(new Vector2d(1.0, 35.00), Math.toRadians(270.00), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(1.0, 24.00), Math.toRadians(270.00), new TranslationalVelConstraint(20))
                .waitSeconds(0.2)
                .afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new AutoBlueLeftHangSpecimenCommandGroup(robotBase)))
                .waitSeconds(0.3)
                .afterTime(0, ()-> CommandScheduler.getInstance().schedule(new AutoSamplePickUpCommandGroup(robotBase)))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(37, 34, Math.toRadians(230)), Math.toRadians(0), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(43, 32.00), Math.toRadians(0.00), new TranslationalVelConstraint(20))
                .afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new BucketExtendUpCommandGroup(robotBase, Shoulder.ShoulderPosition.HIGHBASKET, Extension.ExtensionPosition.HIGHBUCKET)))
                //.waitSeconds(0.2)
                .splineToLinearHeading(new Pose2d(55, 55, Math.toRadians(135)), Math.toRadians(0), new TranslationalVelConstraint(30))
                .waitSeconds(0.5)
                .afterTime(0.9, ()->CommandScheduler.getInstance().schedule(new BucketEjectAndHomeCommandGroup(robotBase, new EjectCommandGroup(robotBase.intakeSubsystem))))
                //.waitSeconds(0.5)
                //.afterTime(0.5, ()-> CommandScheduler.getInstance().schedule(new AutoSamplePickUpCommandGroup(robotBase)))
                //.splineToLinearHeading(new Pose2d(54, 29, Math.toRadians(180)), Math.toRadians(270), new TranslationalVelConstraint(30))
               // .splineToConstantHeading(new Vector2d(54, 32.00), Math.toRadians(0.00), new TranslationalVelConstraint(20))
                /*
                .waitSeconds(2)
                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(8, 34), Math.toRadians(270), new TranslationalVelConstraint(20))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(40, 33, Math.toRadians(230)), Math.toRadians(310), new TranslationalVelConstraint(20))
                //.afterTime(new InstantCommand(()->robotBase.
                /*.setTangent(Math.toRadians(55))

                    .splineToConstantHeading(new Vector2d(40.12, 33.44), Math.toRadians(-12.77))
                .splineToLinearHeading(new Pose2d(50, 56, Math.toRadians(-42)), Math.toRadians(59))
                .setTangent(Math.toRadians(250))
                .splineToLinearHeading(new Pose2d(52, 29, Math.toRadians(180)), Math.toRadians(-87), new TranslationalVelConstraint(20))
                .setTangent(Math.toRadians(45))
                .splineToLinearHeading(new Pose2d(50, 56, Math.toRadians(317)), Math.toRadians(55), new TranslationalVelConstraint(20))
                .setTangent(Math.toRadians(270))
                // .splineTo(new Vector2d(67.55, 56.43), Math.toRadians(254.77))
                .splineToLinearHeading(new Pose2d(54, 31, Math.toRadians(180)), Math.toRadians(45), new TranslationalVelConstraint(20))
                .setTangent(Math.toRadians(45))
                .splineToLinearHeading(new Pose2d(57, 50, Math.toRadians(320)), Math.toRadians(45), new TranslationalVelConstraint(20))
                .setTangent(Math.toRadians(270))
                .splineTo(new Vector2d(50, 19), Math.toRadians(220), new TranslationalVelConstraint(20))
             */   //.splineTo(new Vector2d(24, 12), Math.toRadians(180), new TranslationalVelConstraint(20))
                .build();

        //robotBase.alliance = ITDCrabEnums.EnmAlliance.BLUE;


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
        //DataStorage.alliance = robotBase.alliance;
        DataStorage.dblIMUFinalHeadingRad = robotBase.drive.otos.getPosition().h;

    }
}
