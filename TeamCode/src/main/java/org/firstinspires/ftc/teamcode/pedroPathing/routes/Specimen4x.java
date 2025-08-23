package org.firstinspires.ftc.teamcode.pedroPathing.routes;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.AutoInitCommandGroup;
import org.firstinspires.ftc.teamcode.commands.autocommands.AutoEjectCommandGroup;
import org.firstinspires.ftc.teamcode.commands.autocommands.AutoRetractAndExtendUpCommandGroup;
import org.firstinspires.ftc.teamcode.pedroPathing.commands.FollowPath;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Sweeper;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

@Autonomous(name = "Specimen4x")
public class Specimen4x extends OpMode {
    private int pathState;
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private RobotBase robotBase;
    public SequentialCommandGroup hangSpecimen;
    private PathChain startToHighChamber;
    private PathChain firstSampleAlignment;
    private PathChain firstStepTwoSampleAlignment;
    private PathChain firstSamplePush;
    private PathChain secondSampleAlignment;
    private PathChain secondSamplePush;
    private PathChain thirdSampleScore;
    private PathChain firstLevelAscent;
    private final Pose startPose = new Pose(0, 0, Math.toRadians(0));

    public void buildPaths(){
        startToHighChamber = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(0, 0, Math.toRadians(0)), new Pose(12, -20.5, Math.toRadians(0))))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        firstSampleAlignment = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(12, -23, Math.toRadians(0)), new Pose(-20, -10, Math.toRadians(90))))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(90))
                .addPath(new BezierCurve(new Pose(-20, -10, Math.toRadians(90)), new Pose(-20, -40, Math.toRadians(90))))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();

        firstStepTwoSampleAlignment = follower.pathBuilder()
        .addPath(new BezierCurve(new Pose(-20, -40, Math.toRadians(90)), new Pose(-35, -43, Math.toRadians(90))))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();
        firstSamplePush = follower.pathBuilder()
                .addPath(new BezierLine(new Pose(-35,-43, Math.toRadians(90)), new Pose(-35, -5, Math.toRadians(90))))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();

        secondSampleAlignment = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(-35, -5, Math.toRadians(90)), new Pose(-35, -43, Math.toRadians(90))))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .addPath(new BezierCurve(new Pose(-35, -43, Math.toRadians(90)), new Pose(-40, -43, Math.toRadians(90))))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();

        secondSamplePush = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(-40,-43, Math.toRadians(90)), new Pose(-40, -5, Math.toRadians(90))))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();

       /* thirdSampleScore = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(58, 60, Math.toRadians(180)), new Pose(61, 67, Math.toRadians(145))))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(145))
                .build();

        firstLevelAscent = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(61, 67, Math.toRadians(145)), new Pose(19, 9, Math.toRadians(-90))))
                .setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(-90))
                .build();

        */
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    /*public void autonomousPathUpdate(){
        switch(pathState){
            case 0:
                if(!follower.isBusy()) {
                    follower.followPath(startToBucket, true);
                    setPathState(-1);
                    break;
                }
        }
    }*/

    public void init(){
        CommandScheduler.getInstance().reset();
        CommandScheduler.getInstance().cancelAll();
        CommandScheduler.getInstance().clearButtons();
        robotBase = new RobotBase(hardwareMap);
        pathTimer = new Timer();
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        buildPaths();
        hangSpecimen = new SequentialCommandGroup(
                new InstantCommand(()->follower.setStartingPose(new Pose(0, 0, 0))),
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBER)),
                new FollowPath(follower, startToHighChamber, 1),
                new WaitCommand(500),
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBERCLAMP)),
                new WaitCommand(250),
                new InstantCommand(()->robotBase.clawSubsystem.openClaw()),
                new WaitCommand(500),
                //new WaitUntilCommand(()->!follower.isBusy())
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME)),
                new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.HOME)),
                new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.AUTO)),
                new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PRESUBPICKUP)),
                new FollowPath(follower, firstSampleAlignment, 1),
                new FollowPath(follower, firstStepTwoSampleAlignment, 1),
               // new WaitUntilCommand(()->!follower.isBusy()),
                new FollowPath(follower, firstSamplePush, 1),
                new FollowPath(follower, secondSampleAlignment, 1),
                new FollowPath(follower, secondSamplePush, 1)

                );
        CommandScheduler.getInstance().schedule(new AutoInitCommandGroup(robotBase));
    }
    public void init_loop(){
        CommandScheduler.getInstance().run();
        follower.setStartingPose(startPose);
    }
    public void start(){
        CommandScheduler.getInstance().reset();
        CommandScheduler.getInstance().cancelAll();
        CommandScheduler.getInstance().clearButtons();
        CommandScheduler.getInstance().schedule(hangSpecimen);
    }
    public void loop(){
        follower.update();
        //robotBase.intakeSubsystem.getHueValues();
        robotBase.intakeSubsystem.displaySampleColor();
        //autonomousPathUpdate();
        telemetry.addData("Path State", pathState);
        telemetry.addData("Position", follower.getPose().toString());
        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading", follower.getPose().getHeading());
        telemetry.addData("Set Color", robotBase.intakeSubsystem.getHueValues());
        telemetry.addData("IsRightColor", robotBase.intakeSubsystem.isColor(Intake.ColorList.YELLOW));
        telemetry.update();
        CommandScheduler.getInstance().run();
    }
}
