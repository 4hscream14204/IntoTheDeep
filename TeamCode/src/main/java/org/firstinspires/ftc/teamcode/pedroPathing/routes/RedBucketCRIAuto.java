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

    private final Pose startPose = new Pose(14, 61, Math.toRadians(0));

    public void buildPaths(){
        startToBucket = follower.pathBuilder()
            .addPath(new BezierCurve(new Pose(14, 61, Math.toRadians(0)), new Pose(57, 61, Math.toRadians(145))))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(145))
                .addTemporalCallback(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.TOGGLE))))
                .addTemporalCallback(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.DROPOFF))))
                .addTemporalCallback(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.BUCKETDROPOFF))))
                .addTemporalCallback(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHBUCKET))))
                //.addTemporalCallback(0.8, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.OPEN))))
                //.addTemporalCallback(0.85, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.intakeOuttake())))
                //.addTemporalCallback(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.intakeStop())))
                //.addTemporalCallback(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.ClOSED))))
                //.setPathEndTValueConstraint(1)
                .build();

        firstSampleGrab = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(58, 60, Math.toRadians(145)), new Pose(10, 114), new Pose(47, 47, Math.toRadians(180))))
                .setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(180))
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
        follower.setStartingPose(startPose);
        buildPaths();
        bucketEject = new SequentialCommandGroup(
                new FollowPath(follower, startToBucket, true, 1),
                //new WaitUntilCommand(()->robotBase.extensionSubsystem.isAtPosition(Extension.ExtensionPosition.HIGHBUCKET)),
                new WaitUntilCommand(()->!follower.isBusy()),
                new WaitCommand(1000),
                new InstantCommand(()->robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.OPEN)),
                new WaitCommand(250),
                new InstantCommand(()->robotBase.intakeSubsystem.intakeOuttake()),
                new WaitCommand(250),
                new InstantCommand(()->robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.ClOSED)),
                new InstantCommand(()->robotBase.intakeSubsystem.intakeStop())
        );
        CommandScheduler.getInstance().schedule(new AutoInitCommandGroup(robotBase));
    }
    public void init_loop(){
        CommandScheduler.getInstance().run();
    }
    public void start(){
        CommandScheduler.getInstance().reset();
        CommandScheduler.getInstance().cancelAll();
        CommandScheduler.getInstance().clearButtons();
        CommandScheduler.getInstance().schedule(bucketEject);
    }
    public void loop(){
        follower.update();
        //autonomousPathUpdate();
        telemetry.addData("Path State", pathState);
        telemetry.addData("Position", follower.getPose().toString());
        telemetry.update();
        CommandScheduler.getInstance().run();
    }
}
