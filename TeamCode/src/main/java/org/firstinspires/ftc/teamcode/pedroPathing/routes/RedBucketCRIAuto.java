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
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
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
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

@Autonomous(name = "RedLeft")
public class RedBucketCRIAuto extends OpMode {
    private int pathState;
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private RobotBase robotBase;
    public SequentialCommandGroup bucketEject;
    private PathChain startToBucket;
    private PathChain firstSampleGrab;
    private PathChain secondSampleScore;
    private PathChain secondSampleGrab;
    private PathChain thirdSampleScore;
    private PathChain firstLevelAscent;
    private final Pose startPose = new Pose(14, 61, Math.toRadians(0));

    public void buildPaths(){
        startToBucket = follower.pathBuilder()
            .addPath(new BezierCurve(new Pose(14, 61, Math.toRadians(0)), new Pose(59, 65, Math.toRadians(145))))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(145))
                .build();

        firstSampleGrab = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(59, 65, Math.toRadians(145)), new Pose(48, 59, Math.toRadians(180))))
                .setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(180))
                .build();

        secondSampleScore = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(48, 59, Math.toRadians(180)), new Pose(59, 65, Math.toRadians(145))))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(145))
                .build();

        secondSampleGrab = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(59, 65, Math.toRadians(145)), new Pose(58, 60, Math.toRadians(180))))
                .setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(180))
                .build();

        thirdSampleScore = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(58, 60, Math.toRadians(180)), new Pose(61, 67, Math.toRadians(145))))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(145))
                .build();

        firstLevelAscent = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(59, 65, Math.toRadians(145)), new Pose(49, 51, Math.toRadians(60))))
                .setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(160))
                .build();
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
        bucketEject = new SequentialCommandGroup(
                new InstantCommand(()->follower.setStartingPose(new Pose(14, 61, 0))),
                //new InstantCommand(this::buildPaths),
                //new WaitCommand(250),
                new AutoRetractAndExtendUpCommandGroup(robotBase),
                new FollowPath(follower, startToBucket, true, 1),
                new InstantCommand(()->robotBase.clawSubsystem.openClaw()),
                //new InstantCommand(()->robotBase.intakeSubsystem.intakeSpeed(0.7)),
                //new WaitUntilCommand(()->robotBase.extensionSubsystem.isAtPosition(Extension.ExtensionPosition.HIGHBUCKET)),
                new WaitUntilCommand(()->!follower.isBusy()),
                new WaitCommand(250),
                new AutoEjectCommandGroup(robotBase),
                new WaitCommand(500),
                new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP)),
                new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP)),
                new WaitCommand(250),
                //first specimen grab
                new FollowPath(follower, firstSampleGrab, true, 1),
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.AUTOPREINTAKESAMPLE)),
                new WaitUntilCommand(()->robotBase.extensionSubsystem.isAtPosition(Extension.ExtensionPosition.AUTOPREINTAKESAMPLE)),
                new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.HOME)),
                new WaitUntilCommand(()->robotBase.shoulderSubsystem.isShoulderHome()),
                new InstantCommand(()->robotBase.intakeSubsystem.intakeSpeed(1)),
                new WaitCommand(250),
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBERCLAMP)),
                new WaitCommand(750),
                new AutoRetractAndExtendUpCommandGroup(robotBase),
                new FollowPath(follower, secondSampleScore, true, 1),
                new WaitUntilCommand(()->!follower.isBusy()),
                new WaitCommand(250),
                new AutoEjectCommandGroup(robotBase),
                new FollowPath(follower, secondSampleGrab, true, 1),
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.AUTOPREINTAKESAMPLE)),
                new WaitUntilCommand(()->robotBase.extensionSubsystem.isAtPosition(Extension.ExtensionPosition.AUTOPREINTAKESAMPLE)),
                new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.HOME)),
                new InstantCommand(()->robotBase.intakeSubsystem.intakeSpeed(0.7)),
                new WaitUntilCommand(()->robotBase.shoulderSubsystem.isShoulderHome()),
                new InstantCommand(()->robotBase.intakeSubsystem.intakeSpeed(1)),
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBERCLAMP)),
                new WaitCommand(750),
                new AutoRetractAndExtendUpCommandGroup(robotBase),
                new WaitUntilCommand(()->robotBase.extensionSubsystem.isAtPosition(Extension.ExtensionPosition.HIGHBUCKET)),
                new FollowPath(follower, thirdSampleScore, true, 0.5),
                new WaitUntilCommand(()->!follower.isBusy()),
                new WaitCommand(250),
                new AutoEjectCommandGroup(robotBase)
                /*new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.AUTOPREINTAKESAMPLE)),
                new WaitUntilCommand(()->robotBase.extensionSubsystem.isAtPosition(Extension.ExtensionPosition.AUTOPREINTAKESAMPLE))
                new FollowPath(follower, thirdSampleGrab, true, 1),
                new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.HOME)),
                new InstantCommand(()->robotBase.intakeSubsystem.intakeSpeed(0.7)),
                new WaitUntilCommand(()->robotBase.shoulderSubsystem.isShoulderHome()),
                new InstantCommand(()->robotBase.intakeSubsystem.intakeSpeed(1)),
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBERCLAMP))*/
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
        CommandScheduler.getInstance().schedule(bucketEject);
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
