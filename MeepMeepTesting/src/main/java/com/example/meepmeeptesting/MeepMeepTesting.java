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


        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(14, 61, Math.toRadians(270)))
                .setTangent(Math.toRadians(270))
                //.afterTime(0.39, ()-> CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroupPartOne(robotBase)))
                .splineToConstantHeading(new Vector2d(-2.0, 35.00), Math.toRadians(270.00), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-2.0, 27.00), Math.toRadians(270.00), new TranslationalVelConstraint(20))
                .waitSeconds(0.2)
                //.afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroupPartTwo(robotBase)))
                .waitSeconds(0.4)
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(37, 28), Math.toRadians(270.00), new TranslationalVelConstraint(20))
                //.splineToConstantHeading(new Vector2d(37, 18), Math.toRadians(270), new TranslationalVelConstraint(20))
                .splineToSplineHeading(new Pose2d( 37, 18, Math.toRadians(180)), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(30, 11), Math.toRadians(180), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(20, 11), Math.toRadians(180), new TranslationalVelConstraint(20))
                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }

}