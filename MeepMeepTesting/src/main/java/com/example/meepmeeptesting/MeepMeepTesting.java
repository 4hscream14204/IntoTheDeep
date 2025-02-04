package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
    MeepMeep meepMeep = new MeepMeep(800);

    RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
            // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
            .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)

            .build();


            // hang preload
    myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-14, 61, Math.toRadians(0)))
            .setTangent(Math.toRadians(270))
            //.afterTime(0.39, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroupPartOne(robotBase)))
            .splineToConstantHeading(new Vector2d(-2.0, 28.00), Math.toRadians(270.00), new TranslationalVelConstraint(30))
            .waitSeconds(0.2)
            //.afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroupPartTwo(robotBase)))
            .waitSeconds(0.5)
            .setTangent(Math.toRadians(90))
            .splineToConstantHeading(new Vector2d(-2.0, 32), Math.toRadians(90), new TranslationalVelConstraint(35))
            //drive over to samples and move them to human player area
            .splineToSplineHeading(new Pose2d(-31.0, 32.00,Math.toRadians(180.00)), Math.toRadians(270.00), new TranslationalVelConstraint(30))
            .splineToConstantHeading(new Vector2d(-31.0, 17), Math.toRadians(270), new TranslationalVelConstraint(35))
            .splineToConstantHeading(new Vector2d(-38, 17), Math.toRadians(90),new TranslationalVelConstraint(30))
            .setTangent(Math.toRadians(90))
            //push into player area
            .splineToConstantHeading(new Vector2d(-38, 52), Math.toRadians(90),new TranslationalVelConstraint(35))
            .setTangent(Math.toRadians(270))
            //line up for second sample
            .splineToConstantHeading(new Vector2d(-38, 17), Math.toRadians(270),new TranslationalVelConstraint(35))
            //push to human player
            .splineToConstantHeading(new Vector2d(-44, 17), Math.toRadians(90),new TranslationalVelConstraint(30))
            .splineToConstantHeading(new Vector2d(-44, 44), Math.toRadians(90),new TranslationalVelConstraint(35))
            // go forward and grab specimen
            .splineToConstantHeading(new Vector2d(-44.00, 50), Math.toRadians(90), new TranslationalVelConstraint(35))
            .splineToConstantHeading(new Vector2d(-44.00, 63), Math.toRadians(90), new TranslationalVelConstraint(25))
            //.afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new GrabSpecimenAndHangPosCommandGroup(robotBase)))
            .waitSeconds(0.2)
            //hang specimen
            .setTangent(Math.toRadians(315))
            .splineToSplineHeading(new Pose2d(-4, 28.00, Math.toRadians(0.00)), Math.toRadians(300), new TranslationalVelConstraint(35))
            //.afterTime(0.2, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroupPartTwo(robotBase)))
            .waitSeconds(0.4)
            //go and grab another specimen
            .setTangent(Math.toRadians(90))
            .splineToSplineHeading(new Pose2d(-44.0, 63.00, Math.toRadians(180.00)), Math.toRadians(135), new TranslationalVelConstraint(35))
            //.afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new GrabSpecimenAndHangPosCommandGroup(robotBase)))
            .waitSeconds(0.2)
            //hang specimen
            .setTangent(Math.toRadians(315))
            .splineToSplineHeading(new Pose2d(-4, 28.00, Math.toRadians(0.00)), Math.toRadians(300), new TranslationalVelConstraint(35))

            //.afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroupPartTwo(robotBase)))
            .waitSeconds(0.4)
            //park
            .setTangent(Math.toRadians(90))
            .splineToLinearHeading(new Pose2d(-54.00, 52, Math.toRadians(180)), Math.toRadians(180.00), new TranslationalVelConstraint(50))
            //.afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.HOME))))
            //.afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PRESUBPICKUP))))
            //.afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.clawSubsystem.closeClaw())))

            .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();

    }
}