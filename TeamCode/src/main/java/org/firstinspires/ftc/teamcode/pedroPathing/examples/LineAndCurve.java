package org.firstinspires.ftc.teamcode.pedroPathing.examples;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

@Autonomous(name = "LineAndCurve")
public class LineAndCurve extends OpMode {
    private int pathState;
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private final Pose startPose = new Pose(9.757, 84.983, 0);
    private final Pose endLinePose = new Pose(36.668, 84.983);
    private final Pose submersibleControlPose = new Pose(88.105, 126.073);
    private final Pose submersibleEndPose = new Pose(87.012, 77.319);

    private Path startLine;
    private Path submersibleCurve;

    public void buildPaths(){
        startLine = new Path(new BezierLine(new Point(startPose), new Point(endLinePose)));
        startLine.setLinearHeadingInterpolation(startPose.getHeading(), endLinePose.getHeading());

        submersibleCurve = new Path(new BezierCurve(new Point(endLinePose), new Point(submersibleControlPose), new Point(submersibleEndPose)));
        submersibleCurve.setLinearHeadingInterpolation(endLinePose.getHeading(), submersibleEndPose.getHeading());

    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(startLine);
                setPathState(1);
                break;
            case 1:

                /* You could check for
                - Follower State: "if(!follower.isBusy() {}"
                - Time: "if(pathTimer.getElapsedTimeSeconds() > 1) {}"
                - Robot Position: "if(follower.getPose().getX() > 36) {}"
                */

                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower.isBusy()) {
                    /* Score Preload */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower.followPath(submersibleCurve,true);
                    setPathState(2);
                }
                break;
            case 2:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
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
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);
        buildPaths();
    }
    @Override
    public void loop(){
        // These loop the movements of the robot
        follower.update();
        autonomousPathUpdate();

        // Feedback to Driver Hub
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }
}
