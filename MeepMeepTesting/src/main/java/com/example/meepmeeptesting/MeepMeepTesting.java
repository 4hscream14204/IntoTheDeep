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

            .setTangent(Math.toRadians(270))
            .splineToSplineHeading(new Pose2d(53, 55,Math.toRadians(135.00)), Math.toRadians(45.00))
                    .setTangent(Math.toRadians(225))
            .splineToSplineHeading(new Pose2d(36, 26,Math.toRadians(270)), Math.toRadians(270))

            .splineToConstantHeading(new Vector2d(30, 10), Math.toRadians(180), new TranslationalVelConstraint(30))
            .splineToConstantHeading(new Vector2d(20, 10), Math.toRadians(180), new TranslationalVelConstraint(20))
            .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();

    }
}