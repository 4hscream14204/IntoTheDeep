package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepJack {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(29, 62, Math.toRadians(-90)))
                .splineToConstantHeading(new Vector2d(10, 34), Math.toRadians(-90))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(36, 37), Math.toRadians(-90))
                .setReversed(false)
                .splineToConstantHeading(new Vector2d(36,16),Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(46.53, 16.83), Math.toRadians(90.00))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(45.07, 45.21), Math.toRadians(90))
                /*.splineToConstantHeading(new Vector2d(58.67, 58.80), Math.toRadians(57.24))
                .splineToConstantHeading(new Vector2d(45.21, 45.21), Math.toRadians(-79.36))
                .setReversed(false)
                .splineToConstantHeading(new Vector2d(46.79, 12.74), Math.toRadians(-87.00))
                .splineToConstantHeading(new Vector2d(57.88, 13.00), Math.toRadians(5.44))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(56.69, 62.63), Math.toRadians(86.40))
                .setReversed(false)*/



                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}