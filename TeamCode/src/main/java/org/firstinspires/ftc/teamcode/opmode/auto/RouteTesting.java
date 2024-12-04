package org.firstinspires.ftc.teamcode.opmode.auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.roadrunner.SparkFunOTOSDrive;

@Autonomous
public class RouteTesting extends OpMode {
    public RobotBase robotBase;
    private Action blueRight;
    SparkFunOTOSDrive drive;
    @Override
    public void init (){
        Pose2d beginPose = new Pose2d(14, 61, Math.toRadians(180));
        drive = new SparkFunOTOSDrive (hardwareMap, beginPose);
        blueRight = drive.actionBuilder(beginPose)
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberClosed())))
                .afterTime(0.5, ()-> CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.AUTOHIGHCHAMBERSTART))))
                .waitSeconds(0.25)
                .setTangent(Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(3, 45), Math.toRadians(-90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(3,33.25),Math.toRadians(-90),new TranslationalVelConstraint(20))
                .afterTime(0.25, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.AUTOHIGHCHAMBERCLAMP))))
                .afterTime(1.75, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberOpen())))
                .waitSeconds(1)
                .setTangent(Math.toRadians(90))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.HOME))))
                .splineToSplineHeading(new Pose2d(37, 25, Math.toRadians(90)), Math.toRadians(-90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(37, 6), Math.toRadians(-90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(46,10),Math.toRadians(90),new TranslationalVelConstraint(20))
                .splineToSplineHeading(new Pose2d(53, 55, Math.toRadians(45.00)), Math.toRadians(225),new TranslationalVelConstraint(20))
                .splineToLinearHeading(new Pose2d(57, 15, Math.toRadians(90.00)), Math.toRadians(45),new TranslationalVelConstraint(20))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(54,55, Math.toRadians(45)), Math.toRadians(45),new TranslationalVelConstraint(20))
                .splineToSplineHeading(new Pose2d(46, 12, Math.toRadians(45.00)), Math.toRadians(90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(58,59), Math.toRadians(45),new TranslationalVelConstraint(20))
                .afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.FIRSTLEVELASCENT))))
                .afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberClosed())))
                .splineToSplineHeading(new Pose2d(39, 14, Math.toRadians(90)), Math.toRadians(45),new TranslationalVelConstraint(20))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(25, 12,Math.toRadians(90)), Math.toRadians(180),new TranslationalVelConstraint(20))
                .build();
    }

    @Override
    public void start() {
        Actions.runBlocking(blueRight);
    }

    @Override
    public void loop (){

    }
}
