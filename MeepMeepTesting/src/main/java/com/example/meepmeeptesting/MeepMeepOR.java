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
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-14, 61, Math.toRadians(270)))
                        .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(7.79, 26.00), Math.toRadians(270))
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(40.00, 33, Math.toRadians(230)), Math.toRadians(310))
                .setTangent(45)
                //    .splineToConstantHeading(new Vector2d(40.12, 33.44), Math.toRadians(-12.77))
                .splineToLinearHeading(new Pose2d(54.06, 55.00, Math.toRadians(-42.00)), Math.toRadians(58.68))
                .splineToConstantHeading(new Vector2d(58, 59), Math.toRadians(45))
                .setTangent(180)
                .splineToLinearHeading(new Pose2d(56.16, 38.29, Math.toRadians(180.00)), Math.toRadians(-86.71))
                .setTangent(45)
                .splineToLinearHeading(new Pose2d(54, 55, Math.toRadians(-42.00)), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(58, 59), Math.toRadians(45))
                .setTangent(270)
                // .splineTo(new Vector2d(67.55, 56.43), Math.toRadians(254.77))
                .splineToLinearHeading(new Pose2d(60.25, 31.22, Math.toRadians(210)), Math.toRadians(300))
                .setTangent(180)
                .splineToLinearHeading(new Pose2d(54.99, 55.17, Math.toRadians(-42)), Math.toRadians(90.00))
                .splineToConstantHeading(new Vector2d(58, 59), Math.toRadians(45))
                .setTangent(200)
                .splineToLinearHeading(new Pose2d(48.27, 18.91,Math.toRadians(90)), Math.toRadians(230.56))
                .splineToConstantHeading(new Vector2d(24.40, 12.23), Math.toRadians(180))
                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }

}
