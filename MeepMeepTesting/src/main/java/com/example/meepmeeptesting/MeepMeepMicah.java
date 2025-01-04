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
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)


                .build();
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(14, 61, Math.toRadians(180)))
                .splineToConstantHeading(new Vector2d(7.79, 34.48), Math.toRadians(264.37))
                        .setTangent(45)
                .splineToLinearHeading(new Pose2d(40.12, 33, Math.toRadians(230)), Math.toRadians(310))
                                .setTangent(45)
            //    .splineToConstantHeading(new Vector2d(40.12, 33.44), Math.toRadians(-12.77))
                .splineToLinearHeading(new Pose2d(58.06, 59.25, Math.toRadians(-42.00)), Math.toRadians(58.68))
                        .setTangent(180)
                .splineToLinearHeading(new Pose2d(56.16, 29.29, Math.toRadians(180.00)), Math.toRadians(-86.71))
                        .setTangent(45)
                .splineToLinearHeading(new Pose2d(57.95, 57.54, Math.toRadians(330.00)), Math.toRadians(54.73))
                                .setTangent(270)
               // .splineTo(new Vector2d(67.55, 56.43), Math.toRadians(254.77))
                .splineToLinearHeading(new Pose2d(68.25, 31.22, Math.toRadians(180.00)), Math.toRadians(45))
                        .setTangent(220)
                .splineTo(new Vector2d(63.99, 61.17), Math.toRadians(90.00))
                .splineTo(new Vector2d(48.27, 18.91), Math.toRadians(230.56))
                .splineTo(new Vector2d(24.40, 12.23), Math.toRadians(179.50))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
