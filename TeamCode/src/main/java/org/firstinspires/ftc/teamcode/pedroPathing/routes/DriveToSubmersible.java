package org.firstinspires.ftc.teamcode.pedroPathing.routes;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
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
import org.firstinspires.ftc.teamcode.commands.ExtensionHomeCommandGroup;
import org.firstinspires.ftc.teamcode.commands.ShoulderHomeCommandGroup;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

@Autonomous(name = "Drive to Chamber")
public class DriveToSubmersible extends OpMode {
    RobotBase robotBase;
    private int pathState;
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private Path line;
    private BezierCurve endCurve;
    private PathChain startPath;
    private Pose beginningPose = new Pose(7, 56.000, 0/*Math.toRadians(-90)*/);
    private Pose endLinePose = new Pose(20, 56.000, 0/*Math.toRadians(-90)*/);
    private Pose endCurveControlPoint = new Pose(23.818, 72.328, Point.CARTESIAN);
    private Pose endCurveEndPoint = new Pose(40.206, 72.328, Point.CARTESIAN);

    public void buildPaths(){
        line = new Path(new BezierLine(new Point(beginningPose), new Point(endLinePose)));
        endCurve = new BezierCurve(endLinePose, endCurveControlPoint, endCurveEndPoint);

        startPath = follower.pathBuilder().addPath(line)
                //.addPath(endCurve)
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addTemporalCallback(500, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.NEWHIGHCHAMBER))))
                .addTemporalCallback(750, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBER))))
                .addTemporalCallback(750, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP))))
                .addTemporalCallback(750, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP))))
                .addTemporalCallback(2000, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBERCLAMP))))
                .addTemporalCallback(2500, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.clawSubsystem.openClaw())))
                .addTemporalCallback(3000, ()->CommandScheduler.getInstance().schedule(new ShoulderHomeCommandGroup(robotBase.shoulderSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem)))
                .addTemporalCallback(3000, ()->CommandScheduler.getInstance().schedule(new ExtensionHomeCommandGroup(robotBase.extensionSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem)))
                .build();
                        // Line 2
        /*follower.pathBuilder().addPath(new BezierCurve(new Point(31.684, 56.000, Point.CARTESIAN), new Point(23.818, 72.328, Point.CARTESIAN), new Point(40.206, 72.328, Point.CARTESIAN)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();*/
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(startPath, true);
                setPathState(1);
                break;
            case 1:

                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    //CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.HOME)));
                    //CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.HOME)));
                    setPathState(-1);
                }
                break;
            case 2:
                if(!follower.isBusy()) {
                    /* Level 1 Ascent */

                    /* Set the state to a Case we won't use or define, so it just stops running an new paths */
                    setPathState(-1);
                }
        }
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void init(){
        CommandScheduler.getInstance().reset();
        robotBase = new RobotBase(hardwareMap);
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(beginningPose);
        buildPaths();
        robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.HOME);
        robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.HOME);
        robotBase.clawSubsystem.closeClaw();
    }
    @Override
    public void loop(){
        follower.update();
        autonomousPathUpdate();

        // Feedback to Driver Hub
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
        CommandScheduler.getInstance().run();
    }

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

}
