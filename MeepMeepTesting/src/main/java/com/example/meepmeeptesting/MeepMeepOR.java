package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepOR {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)

                        .build();
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(14, 61, Math.toRadians(-90)))
                .splineToConstantHeading(new Vector2d(-4.0, 22.00), Math.toRadians(180.00), new TranslationalVelConstraint(20))
                .setTangent(180)
                .splineToConstantHeading(new Vector2d(0.0, 40.00), Math.toRadians(180.00), new TranslationalVelConstraint(20))
                .setTangent(180)
                .splineTo(new Vector2d(-33.00, 40.00), Math.toRadians(90.00), new TranslationalVelConstraint(20))
                .splineToLinearHeading(new Pose2d(-35.96, 40.00, Math.toRadians(50.00)), Math.toRadians(120.00), new TranslationalVelConstraint(20))
                .waitSeconds(0.1)
                .splineToLinearHeading(new Pose2d(-41.00, 34.00, Math.toRadians(120.00)), Math.toRadians(200.00), new TranslationalVelConstraint(20))
                .waitSeconds(0.1)
                .splineToLinearHeading(new Pose2d(-45.90, 41.00, Math.toRadians(50.00)), Math.toRadians(120.00), new TranslationalVelConstraint(20))
                .waitSeconds(0.1)
                .splineToLinearHeading(new Pose2d(-50.00, 34.00, Math.toRadians(120.00)), Math.toRadians(200.00), new TranslationalVelConstraint(20))
                .waitSeconds(0.1)
                .splineToLinearHeading(new Pose2d(-54.00, 39.52, Math.toRadians(20.00)), Math.toRadians(120.00), new TranslationalVelConstraint(20))
                .waitSeconds(0.1)
                .splineToSplineHeading(new Pose2d(-49.00, 55.84, Math.toRadians(180.00)), Math.toRadians(87.06), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-5.86, 29.00), Math.toRadians(270.00), new TranslationalVelConstraint(20))
                .setTangent(180)
                .splineToConstantHeading(new Vector2d(-49.00, 56.00), Math.toRadians(150.00), new TranslationalVelConstraint(20))
                .setTangent(180)
                .splineToConstantHeading(new Vector2d(-2.60, 28.70), Math.toRadians(270.00), new TranslationalVelConstraint(20))
                .setTangent(180)
                .splineToConstantHeading(new Vector2d(-49.31, 55.98), Math.toRadians(150.00), new TranslationalVelConstraint(20))
                .setTangent(180)
                .splineToConstantHeading(new Vector2d(0.00, 28.99), Math.toRadians(270.00), new TranslationalVelConstraint(20))
                .setTangent(180)
                .splineToConstantHeading(new Vector2d(-62.81, 62.66), Math.toRadians(90.00), new TranslationalVelConstraint(20))
                .setTangent(180)
                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }

}
