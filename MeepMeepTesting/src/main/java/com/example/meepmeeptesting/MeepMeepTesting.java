package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
//import com.arcrobotics.ftclib.command.CommandScheduler;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
    MeepMeep meepMeep = new MeepMeep(700);

    RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
            // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
            .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)

            .build();


            // hang preload
    myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(14, 61, Math.toRadians(0)))
            //.afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.TOGGLE))))
            //.afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.MAXSHOULDERUPPOSITION))))
            //   .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.MAX))))
            //.afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.BUCKETDROPOFF))))
            //.afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.DROPOFF))))
            .setTangent(Math.toRadians(270))
            .splineToSplineHeading(new Pose2d(57, 58,Math.toRadians(135.00)), Math.toRadians(45.00))
            //.afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.OPEN))))
            //.afterTime(0.2, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.intakeOuttake())))
            //.afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.intakeStop())))
            //.afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP))))
            //.afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP))))
            //.afterTime(1.5, ()-> CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME))))
            .waitSeconds(1)
            //.afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.ClOSED))))
            .setTangent(Math.toRadians(225))
            .splineToLinearHeading(new Pose2d(55,53,Math.toRadians(140)),Math.toRadians(140),new TranslationalVelConstraint(30))
            //.afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.intakeSubsystem.intakeSpeed(0.8)))))
            //.afterTime(0.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.HOME))))
            //.afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.LOWBUCKET))))
            //.afterTime(2, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME))))
            //.afterTime(2.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.intakeStop())))
            //.afterTime(2.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.TOGGLE))))
            //.afterTime(2.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHBUCKET))))
            .waitSeconds(3)
            .splineToLinearHeading(new Pose2d(57,58,Math.toRadians(135)),Math.toRadians(45),new TranslationalVelConstraint(30))
            //.afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.BUCKETDROPOFF))))
            //.afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.DROPOFF))))
            //.afterTime(0.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.intakeSpeed(0.8))))



            .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();

    }
}