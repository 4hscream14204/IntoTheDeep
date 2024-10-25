package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepMicah {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-25, 61, Math.toRadians(-90)))
                .splineToConstantHeading(new Vector2d(-12, 32), Math.toRadians(-90.00))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-36, 34), Math.toRadians(-90.00))
                .setReversed(false)
                .splineToConstantHeading(new Vector2d(-36, 13), Math.toRadians(-90.00))
                .splineToConstantHeading(new Vector2d(-47, 14), Math.toRadians(90.00))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-47, 31), Math.toRadians(90.00))
                .splineToConstantHeading(new Vector2d(-47, 57), Math.toRadians(90.00))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}