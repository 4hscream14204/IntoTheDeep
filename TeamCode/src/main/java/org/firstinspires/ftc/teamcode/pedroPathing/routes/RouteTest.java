package org.firstinspires.ftc.teamcode.pedroPathing.routes;

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

@Autonomous(name = "Route Test")
public class RouteTest extends OpMode {
    private Path line;
    private Path endCurve;
    private int pathState;
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private Pose beginningPose = new Pose(9.757, 84.983, Math.toRadians(-90));
    private Pose endLinePose = new Pose(36.668, 84.983, Math.toRadians(-90));

    private Pose partthreestartpose = new Pose(36.668, 84.983);
    private Pose endCurveControlPoint = new Pose(9.305, 64.468);
    private Pose endCurveEndPoint = new Pose(11.520, 37.883);
    private PathChain startPath;


    private final Pose bucketControlPose = new Pose(43.921,100.515);
    private final Pose partone = new Pose(9.757, 84.983);
    private final Pose parttwo = new Pose(36.668, 84.983);

    private final Pose partthree = new Pose(36.668, 84.983);
    private final Pose partfour = new Pose(9.305, 64.468);
    private final Pose partfive = new Pose(11.520, 37.883);
    public void buildPaths(){
        line = new Path(new BezierLine(new Point(partone), new Point(parttwo)));
        endCurve = new Path(new BezierCurve(partthree, partfour, partfive));

        startPath = follower.pathBuilder().addPath(line)
                .addPath(endCurve)
                .setConstantHeadingInterpolation(Math.toRadians(-90))
                .build();
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(startPath, true);
                setPathState(1);
                break;
            case 1:

                /* You could check for
                - Follower State: "if(!follower.isBusy() {}"
                - Time: "if(pathTimer.getElapsedTimeSeconds() > 1) {}"
                - Robot Position: "if(follower.getPose().getX() > 36) {}"
                */

                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if (!follower.isBusy()) {
                    /* Score Preload */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                  //  follower.followPath(bucketCurve, true);
                    setPathState(-1);
                }
                break;
            case 2:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if (!follower.isBusy()) {
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
       // follower.setStartingPose(startPose);
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
        telemetry.addData("heading", Math.toDegrees(follower.getPose().getHeading()));
        telemetry.update();
    }
}
