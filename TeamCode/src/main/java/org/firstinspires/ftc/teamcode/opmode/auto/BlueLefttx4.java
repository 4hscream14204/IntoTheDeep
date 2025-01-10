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
        startPose = new Pose2d(14, 61, Math.toRadians(270));
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
                .splineToConstantHeading(new Vector2d(7.79, 26.00), Math.toRadians(270), new TranslationalVelConstraint(20))
                .splineToLinearHeading(new Pose2d(40.00, 33, Math.toRadians(230)), Math.toRadians(310))
                .setTangent(45)
                //    .splineToConstantHeading(new Vector2d(40.12, 33.44), Math.toRadians(-12.77))
                .splineToLinearHeading(new Pose2d(54.06, 55.00, Math.toRadians(-42.00)), Math.toRadians(58.68))
                .splineToConstantHeading(new Vector2d(58, 59), Math.toRadians(45))
                .setTangent(180)
                .splineToLinearHeading(new Pose2d(56.16, 38.29, Math.toRadians(180.00)), Math.toRadians(-86.71))
                .setTangent(45)
                .splineToLinearHeading(new Pose2d(54, 55, Math.toRadians(-42.00)), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(58, 59), Math.toRadians(45))
                .setTangent(270)
                // .splineTo(new Vector2d(67.55, 56.43), Math.toRadians(254.77))
                .splineToLinearHeading(new Pose2d(60.25, 31.22, Math.toRadians(210)), Math.toRadians(300))
                .setTangent(180)
                .splineToLinearHeading(new Pose2d(54.99, 55.17, Math.toRadians(-42)), Math.toRadians(90.00))
                .splineToConstantHeading(new Vector2d(58, 59), Math.toRadians(45))
                .setTangent(200)
                .splineToLinearHeading(new Pose2d(48.27, 18.91,Math.toRadians(90)), Math.toRadians(230.56))
                .splineToConstantHeading(new Vector2d(24.40, 12.23), Math.toRadians(180))
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
