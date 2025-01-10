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
import org.firstinspires.ftc.teamcode.base.ITDCrabEnums;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

@Autonomous (name = "BlueRight4x")
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
        startPose = new Pose2d(-14, 61, Math.toRadians(270));
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
                .splineToConstantHeading(new Vector2d(-6.0, 26.00), Math.toRadians(270.00), new TranslationalVelConstraint(20))
                //    .setTangent(90)
                //line up for first specimen
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-6.0, 30), Math.toRadians(90), new TranslationalVelConstraint(20))
                // .setTangent(90)
                .splineToLinearHeading(new Pose2d(-34.00, 35.00, Math.toRadians(130.00)), Math.toRadians(180.00), new TranslationalVelConstraint(20))
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(-35.00, 40, Math.toRadians(50)), Math.toRadians(90), new TranslationalVelConstraint(20))
                //  .splineToLinearHeading(new Pose2d(-35.96, 40.00, Math.toRadians(50.00)), Math.toRadians(120.00), new TranslationalVelConstraint(20))
                .setTangent(Math.toRadians(200))
                .splineToLinearHeading(new Pose2d(-44.00, 34.00, Math.toRadians(120.00)), Math.toRadians(200.00), new TranslationalVelConstraint(20))
                .waitSeconds(0.1)

                .splineToLinearHeading(new Pose2d(-45.90, 41.00, Math.toRadians(50.00)), Math.toRadians(120.00), new TranslationalVelConstraint(20))
                .waitSeconds(0.1)
                .setTangent(Math.toRadians(120))
                .splineToLinearHeading(new Pose2d(-50.00, 34.00, Math.toRadians(120.00)), Math.toRadians(200.00), new TranslationalVelConstraint(20))
                .waitSeconds(0.1)
                .splineToLinearHeading(new Pose2d(-58.00, 39.52, Math.toRadians(20.00)), Math.toRadians(120.00), new TranslationalVelConstraint(20))
                .waitSeconds(0.1)
                .splineToSplineHeading(new Pose2d(-49.00, 55.84, Math.toRadians(180.00)), Math.toRadians(87.06), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-49.00, 60.00), Math.toRadians(90.00), new TranslationalVelConstraint(20))
                .waitSeconds(0.1)
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(-6.00, 29.00, Math.toRadians(180.00)) , Math.toRadians(270.00), new TranslationalVelConstraint(30))
                .waitSeconds(0.1)
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-49.00, 56.00), Math.toRadians(90.00), new TranslationalVelConstraint(30))
                .splineToConstantHeading(new Vector2d(-49, 60.00), Math.toRadians(90.00), new TranslationalVelConstraint(20))
                .waitSeconds(0.1)
                .setTangent(Math.toRadians(320))
                .splineToConstantHeading(new Vector2d(-5.60, 28.70), Math.toRadians(270.00), new TranslationalVelConstraint(30))
                .waitSeconds(0.1)
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-49.31, 55.98), Math.toRadians(90.00), new TranslationalVelConstraint(30))
                .splineToConstantHeading(new Vector2d(-49, 60.00), Math.toRadians(90.00), new TranslationalVelConstraint(20))
                .waitSeconds(0.1)
                .setTangent(Math.toRadians(320))
                .splineToConstantHeading(new Vector2d(-2.00, 28.99), Math.toRadians(270.00), new TranslationalVelConstraint(30))
                .waitSeconds(0.1)
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-61.81, 62.66), Math.toRadians(90.00), new TranslationalVelConstraint(30))
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
