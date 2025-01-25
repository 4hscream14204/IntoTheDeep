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
import org.firstinspires.ftc.teamcode.commands.GrabSpecimenAndHangPosCommandGroup;
import org.firstinspires.ftc.teamcode.commands.HangSpecimenAutoCommandGroupPartOne;
import org.firstinspires.ftc.teamcode.commands.HangSpecimenAutoCommandGroupPartTwo;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
@Disabled
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
        startPose = new Pose2d(-14, 61, Math.toRadians(0));
        robotBase =new RobotBase(hardwareMap);
        armController = new GamepadEx(gamepad2);
        baseController = new GamepadEx(gamepad1);
        CommandScheduler.getInstance().reset();
        robotBase.drive.pose = startPose;
        telemetryPacket = new TelemetryPacket();
        robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.INIT);
        robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.HOME);
        robotBase.elbowSubsystem.enmElbowPosition = Elbow.ElbowPosition.HOME;

        baseController.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new InstantCommand(
                        ()-> waitSec++
                ));

        baseController.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(new InstantCommand(
                        ()-> waitSec--
                ));


        blueRightx4Action = robotBase.drive.actionBuilder(startPose)
                // hang preload
                .setTangent(Math.toRadians(270))
                .afterTime(0.39, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroupPartOne(robotBase)))
                .splineToConstantHeading(new Vector2d(-2.0, 35.00), Math.toRadians(270.00), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-2.0, 25.00), Math.toRadians(270.00), new TranslationalVelConstraint(20))
                .waitSeconds(0.2)
                 .afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroupPartTwo(robotBase)))
                .waitSeconds(0.4)
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-2.0, 40), Math.toRadians(90), new TranslationalVelConstraint(30))
                //drive over to samples and move them to human player area
                .splineToSplineHeading(new Pose2d(-34.0, 46.00, Math.toRadians(90.00)), Math.toRadians(270.00), new TranslationalVelConstraint(30))
                //.setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-34.0, 17), Math.toRadians(270), new TranslationalVelConstraint(30))
                //.splineToLinearHeading(new Pose2d(-42.0, 12.00, Math.toRadians(90.00)), Math.toRadians(200.00), new TranslationalVelConstraint(50))
                // .setTangent(180)
                .splineToConstantHeading(new Vector2d(-45, 17), Math.toRadians(90),new TranslationalVelConstraint(30))
                .setTangent(Math.toRadians(90))
                //push into player area
                .splineToConstantHeading(new Vector2d(-45, 55), Math.toRadians(90),new TranslationalVelConstraint(35))
                .setTangent(Math.toRadians(270))
                //line up for second sample
                .splineToConstantHeading(new Vector2d(-45, 17), Math.toRadians(270),new TranslationalVelConstraint(35))
                // .setTangent(90)
                //push to human player
                .splineToConstantHeading(new Vector2d(-55, 18), Math.toRadians(90),new TranslationalVelConstraint(30))
                .splineToConstantHeading(new Vector2d(-55, 50), Math.toRadians(90),new TranslationalVelConstraint(35))
                .setTangent(Math.toRadians(270))
                //back up and wait
                .splineToLinearHeading(new Pose2d(-47.25, 45.00, Math.toRadians(180.00)), Math.toRadians(270.00), new TranslationalVelConstraint(30))
                .waitSeconds(0.5)
                // go forward and grab specimen
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-47.25, 54), Math.toRadians(90), new TranslationalVelConstraint(35))
                .splineToConstantHeading(new Vector2d(-47.25, 64), Math.toRadians(90), new TranslationalVelConstraint(35))
                .afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new GrabSpecimenAndHangPosCommandGroup(robotBase)))
                .waitSeconds(0.2)
                //hang specimen
                .setTangent(Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(-4.50, 35.00, Math.toRadians(0.00)), Math.toRadians(270.00), new TranslationalVelConstraint(35))
                .waitSeconds(0.2)
                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-4.5, 25), Math.toRadians(270), new TranslationalVelConstraint(35))
                //.waitSeconds(0.2)
                //go back and grab next specimen
                .afterTime(0.2, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroupPartTwo(robotBase)))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-46.8, 58.00, Math.toRadians(180.00)), Math.toRadians(90.00), new TranslationalVelConstraint(35))
                .splineToConstantHeading(new Vector2d(-46.8, 64), Math.toRadians(90), new TranslationalVelConstraint(35))
                 .afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new GrabSpecimenAndHangPosCommandGroup(robotBase)))
                .waitSeconds(0.2)
                //hang specimen
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(-6.00, 35.00, Math.toRadians(0.00)), Math.toRadians(270.00), new TranslationalVelConstraint(35))
                .splineToConstantHeading(new Vector2d(-6, 25), Math.toRadians(270), new TranslationalVelConstraint(35))
                .afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroupPartTwo(robotBase)))
                .waitSeconds(0.2)
                //park
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.HOME))))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.AUTOINIT))))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-58.00, 55, Math.toRadians(180)), Math.toRadians(180.00), new TranslationalVelConstraint(50))

                                .build();

        robotBase.alliance = ITDCrabEnums.EnmAlliance.BLUE;


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
