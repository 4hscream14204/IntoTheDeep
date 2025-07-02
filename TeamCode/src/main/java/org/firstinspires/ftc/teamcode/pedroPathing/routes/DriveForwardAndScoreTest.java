package org.firstinspires.ftc.teamcode.pedroPathing.routes;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;

@Autonomous(name = "DriveForwardAndScore")
public class DriveForwardAndScoreTest extends OpMode {
    RobotBase robotBase;
    private int pathState;
    private Follower follower;
    private Path leaveWallPath;
    private Path turnToSubPath;
    //private PathChain scoreSpecimen1;
    private Timer pathTimer;

    private final Pose leaveWall = new Pose(0, 0, Math.toRadians(-90));
    private final Pose endLeaveWall = new Pose(20, 0, Math.toRadians(-90));
    private final Pose turnToSubmersible = new Pose(20, -20, Math.toRadians(-90));

    public void buildPaths(){
        leaveWallPath = new Path(new BezierLine(new Point(leaveWall), new Point(endLeaveWall)));
        leaveWallPath.setConstantHeadingInterpolation(Math.toRadians(-90));

        turnToSubPath = new Path(new BezierLine(new Point(endLeaveWall), new Point(turnToSubmersible)));
        turnToSubPath.setConstantHeadingInterpolation(Math.toRadians(-90));
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    public void autonomousPathUpdate(){
        switch(pathState){
            case 0:
                CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBER)));
                setPathState(1);
                break;

            case 1:
                follower.followPath(leaveWallPath, true);
                setPathState(2);
                break;

            case 2:
                follower.followPath(turnToSubPath, true);
                setPathState(-1);
                break;
        }
    }

    public void init(){
        CommandScheduler.getInstance().reset();
        robotBase = new RobotBase(hardwareMap);
        pathTimer = new Timer();
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(leaveWall);
        CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.TOGGLE)));
        buildPaths();
    }

    public void init_loop(){
        CommandScheduler.getInstance().run();
    }

    public void loop(){
        follower.update();
        autonomousPathUpdate();
        telemetry.addData("Path State", pathState);
        telemetry.addData("Position", follower.getPose().toString());
        telemetry.update();
        CommandScheduler.getInstance().run();
    }
}
