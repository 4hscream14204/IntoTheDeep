package org.firstinspires.ftc.teamcode.pedroPathing.routes;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.ITDCrabEnums;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.AutoInitCommandGroup;
import org.firstinspires.ftc.teamcode.commands.GrabSpecimenAndHangPosCommandGroup;
import org.firstinspires.ftc.teamcode.pedroPathing.commands.FollowPath;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;

@Autonomous(name = "RedSpecimen4x")
public class RedSpecimen4x extends OpMode {
    private int pathState;
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private RobotBase robotBase;
    public SequentialCommandGroup hangSpecimen;
    private PathChain startToHighChamber;
    private PathChain firstSampleAlignmentPartOne;
    private PathChain firstSampleAlignmentPartTwo;
    private PathChain firstSampleAlignmentPartThree;
    private PathChain firstSamplePush;
    private PathChain secondSampleAlignmentPartOne;
    private PathChain secondSampleAlignmentPartTwo;
    private PathChain secondSamplePush;
    private PathChain  thirdSampleAlignmentPartOne;
    private PathChain  thirdSampleAlignmentPartTwo;
    private PathChain  thirdSamplePush;
    private PathChain firstSpecimenPickUp;
    private PathChain firstSpecimenHang;
    private PathChain firstSpecimenHangPartTwo;
    private PathChain secondSpecimenPickUp;
    private PathChain secondSpecimenHang;
    private PathChain secondSpecimenHangPartTwo;
    private final Pose startPose = new Pose(0, 0, Math.toRadians(0));

    public void buildPaths(){
        startToHighChamber = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(0, 0, Math.toRadians(0)), new Pose(12, -35.5, Math.toRadians(0))))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();



        /*
         .addPath(
        // Line 1
        new BezierCurve(
          new Point(4.440, 40.150, Point.CARTESIAN),
          new Point(73.351, 25.673, Point.CARTESIAN),
          new Point(68.718, 7.914, Point.CARTESIAN),
          new Point(79.914, 4.440, Point.CARTESIAN),
          new Point(4.826, 9.845, Point.CARTESIAN)
        )
      )
         */
        firstSampleAlignmentPartOne = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(12, -35.5, Math.toRadians(0)), new Pose(-20, -13, Math.toRadians(90))))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(90))
                .build();
        firstSampleAlignmentPartTwo = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(-20, -13, Math.toRadians(90)), new Pose(-25, -56, Math.toRadians(90))))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();
        firstSampleAlignmentPartThree = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(-25, -56, Math.toRadians(90)), new Pose(-37, -56, Math.toRadians(90))))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();
        firstSamplePush = follower.pathBuilder()
                .addPath(new BezierLine(new Pose(-37,-54, Math.toRadians(90)), new Pose(-37, -5, Math.toRadians(90))))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();

        secondSampleAlignmentPartOne = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(-37, -5, Math.toRadians(90)), new Pose(-33, -52, Math.toRadians(90))))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();

        secondSampleAlignmentPartTwo = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(-33, -52, Math.toRadians(90)), new Pose(-42, -50, Math.toRadians(90))))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();

        secondSamplePush = follower.pathBuilder()
                .addPath(new BezierLine(new Pose(-42,-50, Math.toRadians(90)), new Pose(-40, -5, Math.toRadians(90))))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();

        thirdSampleAlignmentPartOne = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(-40, -5, Math.toRadians(90)), new Pose(-40, -48, Math.toRadians(90))))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();

        thirdSampleAlignmentPartTwo = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(-40, -50, Math.toRadians(90)), new Pose(-50, -50, Math.toRadians(90))))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();

        thirdSamplePush = follower.pathBuilder()
                .addPath(new BezierLine(new Pose(-50,-48, Math.toRadians(90)), new Pose(-49, -5, Math.toRadians(90))))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();
        firstSpecimenPickUp = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(-49,-5, Math.toRadians(90)), new Pose(-28, 3, Math.toRadians(180))))
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                .build();
        firstSpecimenHang = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(-28,-13, Math.toRadians(180)), new Pose(13, -18.0, Math.toRadians(0))))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(0))
                .build();
        firstSpecimenHangPartTwo = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(-28,-23, Math.toRadians(180)), new Pose(13, -42.0, Math.toRadians(0))))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
        secondSpecimenPickUp = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(13,-38, Math.toRadians(0)), new Pose(-28, 4, Math.toRadians(180))))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(180))
                .build();
        secondSpecimenHang = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(-28,0, Math.toRadians(180)), new Pose(11, -18.0, Math.toRadians(0))))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(0))
                .build();
        secondSpecimenHangPartTwo = follower.pathBuilder()
                .addPath(new BezierCurve(new Pose(11,-18, Math.toRadians(180)), new Pose(11, -38.0, Math.toRadians(0))))
                .setConstantHeadingInterpolation(Math.toRadians(0))
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
        GamepadEx baseController = new GamepadEx(gamepad1);
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        buildPaths();
        baseController.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new InstantCommand(()-> DataStorage.strategy = ITDCrabEnums.Strategy.SPECIMENSTOCKPILE));

        baseController.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(new InstantCommand(()-> DataStorage.strategy = ITDCrabEnums.Strategy.SPECIMENBASICCYCLE));

        baseController.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(new InstantCommand(()-> DataStorage.strategy = ITDCrabEnums.Strategy.BUCKETBASICCYCLE));
        hangSpecimen = new SequentialCommandGroup(
                new InstantCommand(()->robotBase.shoulderSubsystem.stopInPlace()),
                new InstantCommand(()->follower.setStartingPose(new Pose(0, 0, 0))),
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBER)),
                new FollowPath(follower, startToHighChamber, false, 1),
                new WaitCommand(500),
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBERCLAMP)),
                new WaitCommand(250),
                new InstantCommand(()->robotBase.clawSubsystem.openClaw()),
                new WaitCommand(250),
                //new WaitUntilCommand(()->!follower.isBusy())
                new InstantCommand(()->robotBase.shoulderSubsystem.stopInPlace()),
                //new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOPARK)),
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME)),
               // new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.HOME)),
              //  new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.HOME)),
               // new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.HOME)),
                new FollowPath(follower, firstSampleAlignmentPartOne, false, 1),
                //new WaitUntilCommand(()->!follower.isBusy()),
                new FollowPath(follower, firstSampleAlignmentPartTwo, false, 1),
                //new WaitUntilCommand(()->!follower.isBusy()),
                new FollowPath(follower, firstSamplePush, false,1),
                new FollowPath(follower, secondSampleAlignmentPartOne, false, 1),
                new FollowPath(follower, secondSampleAlignmentPartTwo, false, 1),
                new FollowPath(follower, secondSamplePush, false, 1),
                new FollowPath(follower, thirdSampleAlignmentPartOne, false, 1),
                new FollowPath(follower, thirdSampleAlignmentPartTwo,false, 1),
                new FollowPath(follower, thirdSamplePush, false, 1),
                new FollowPath(follower, firstSpecimenPickUp, false, 1),
                new WaitCommand(250),
                new GrabSpecimenAndHangPosCommandGroup(robotBase),
                new FollowPath(follower, firstSpecimenHang, 1),
                new FollowPath(follower, firstSpecimenHangPartTwo, false, 1),
                new WaitCommand(500),
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBERCLAMP)),
                new WaitCommand(500),
                new InstantCommand(()->robotBase.clawSubsystem.openClaw()),
                new InstantCommand(()->robotBase.shoulderSubsystem.stopInPlace()),
               // new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOPARK)),
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME)),
                new FollowPath(follower, secondSpecimenPickUp, false,1),
                //new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME)),
                new WaitCommand(500),
                new GrabSpecimenAndHangPosCommandGroup(robotBase),
                new FollowPath(follower,secondSpecimenHang, false,1),
                new FollowPath(follower, secondSpecimenHangPartTwo, false, 1),
                new WaitCommand(250),
                new InstantCommand(()-> robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBERCLAMP)),
                new WaitCommand(250),
                new InstantCommand(()->robotBase.clawSubsystem.openClaw())
                );
        CommandScheduler.getInstance().schedule(new AutoInitCommandGroup(robotBase));
        robotBase.alliance = ITDCrabEnums.EnmAlliance.RED;
    }
    public void init_loop(){
        CommandScheduler.getInstance().run();
        follower.setStartingPose(startPose);
    }
    public void start(){
        follower.setPose(new Pose(0,0,Math.toRadians(0)));
        CommandScheduler.getInstance().reset();
        CommandScheduler.getInstance().cancelAll();
        CommandScheduler.getInstance().clearButtons();
        CommandScheduler.getInstance().schedule(hangSpecimen);}
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
