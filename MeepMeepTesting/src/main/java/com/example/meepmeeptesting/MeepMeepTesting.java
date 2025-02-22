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
    myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-14, 61, Math.toRadians(0)))
            .setTangent(Math.toRadians(270))
            //.afterTime(0.0, ()-> CommandScheduler.getInstance().schedule(new SpecimenPickupAutoCommandGroup(robotBase)))
            .splineToConstantHeading(new Vector2d(-2.0, 28.00), Math.toRadians(270.00), new TranslationalVelConstraint(45))

            //.waitSeconds(0.1)
            //.afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroup(robotBase)))
            .waitSeconds(0.1)

            .setTangent(Math.toRadians(90))
            .splineToConstantHeading(new Vector2d(-2.0, 36), Math.toRadians(90), new TranslationalVelConstraint(35))

            //drive over to samples and move them to human player area
            .splineToSplineHeading(new Pose2d(-29.0, 36.00,Math.toRadians(270.00)), Math.toRadians(270.00), new TranslationalVelConstraint(35))
           // .afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new ShoulderHomeCommandGroup(robotBase.shoulderSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem)))
            //.afterTime(0.1, ()->CommandScheduler.getInstance().schedule( new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.MIN))))
            //.afterTime(0.1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.HOME))))
            .splineToConstantHeading(new Vector2d(-29.0, 15), Math.toRadians(270), new TranslationalVelConstraint(35))
            .splineToConstantHeading(new Vector2d(-42, 15), Math.toRadians(90),new TranslationalVelConstraint(28))
            .setTangent(Math.toRadians(90))
            //push into player area
            .splineToConstantHeading(new Vector2d(-42, 40), Math.toRadians(90),new TranslationalVelConstraint(45))
            //.setTangent(Math.toRadians(270))
            //line up for second sample
            .splineToConstantHeading(new Vector2d(-42, 16), Math.toRadians(270),new TranslationalVelConstraint(45))
            //push to human player
            .splineToConstantHeading(new Vector2d(-48, 16), Math.toRadians(90),new TranslationalVelConstraint(28))
            .splineToConstantHeading(new Vector2d(-48, 46), Math.toRadians(90),new TranslationalVelConstraint(45))
            //Line up for third sample
            .splineToConstantHeading(new Vector2d(-46, 16), Math.toRadians(270),new TranslationalVelConstraint(45))
            .splineToConstantHeading(new Vector2d(-56, 16), Math.toRadians(90),new TranslationalVelConstraint(28))
            //.afterTime(0.0, ()->CommandScheduler.getInstance().schedule(  new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.TOGGLE))))
            //.afterTime(0.1, ()->CommandScheduler.getInstance().schedule( new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP))))
            //.afterTime(0.1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP))))
            .splineToConstantHeading(new Vector2d(-60, 44), Math.toRadians(90),new TranslationalVelConstraint(45))
            //.setTangent(Math.toRadians(270))

            // go forward and grab specimen
            .splineToSplineHeading(new Pose2d(-47, 50.00, Math.toRadians(180.00)), Math.toRadians(90), new TranslationalVelConstraint(35))
            //.splineToConstantHeading(new Vector2d(-47.00, 50), Math.toRadians(90), new TranslationalVelConstraint(35))
            .splineToConstantHeading(new Vector2d(-47.00, 64), Math.toRadians(90), new TranslationalVelConstraint(35))
            .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();

    }
}