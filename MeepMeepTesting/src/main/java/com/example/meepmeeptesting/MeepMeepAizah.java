package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepAizah {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
// }Blue left; hang & park

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-9, 60, Math.toRadians(-90)))
                        .setTangent(Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(-12, 34), Math.toRadians(-90),new TranslationalVelConstraint(20))
                    .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-35, 24), Math.toRadians(-90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-45, 12), Math.toRadians(-90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-45, 57), Math.toRadians(-90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-36, 12), Math.toRadians(-90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-58, 11), Math.toRadians(-90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-58, 58), Math.toRadians(-90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-28, 57), Math.toRadians(-90),new TranslationalVelConstraint(20))

                .splineToConstantHeading(new Vector2d(-12 ,33), Math.toRadians(-90),new TranslationalVelConstraint(20))
                .build());
         /*       .splineToConstantHeading(new Vector2d(-36.52, 28.73), Math.toRadians(-90.00))
                .setReversed(true)
                .splineTo(new Vector2d(-39.26, 17.51), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(-46.59, 58.95), Math.toRadians(-90))

*/






               /* .splineToConstantHeading(new Vector2d(13, 45), Math.toRadians(-90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(13,32),Math.toRadians(-90),new TranslationalVelConstraint(20))
                        .setTangent(Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(38,32,Math.toRadians(180)),Math.toRadians(-90),new TranslationalVelConstraint(20))
                //.splineToConstantHeading(new Vector2d(38, 38), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(35, 14), Math.toRadians(-90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(46,10),Math.toRadians(-90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(53, 61), Math.toRadians(-candle clay things paintings chrotchet90.00),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(40.27, 15.78), Math.toRadians(-90.00),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(58,10),Math.toRadians(-90),new TranslationalVelConstraint(20))
                //.splineToConstantHeading(new Vector2d(45.88, 13.98), Math.toRadians(-90),new TranslationalVelConstraint(20))
               // .splineToConstantHeading(new Vector2d(48.51, 22.07), Math.toRadians(-90.00),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(58, 61), Math.toRadians(-90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(39, 14), Math.toRadians(-90),new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(25, 12), Math.toRadians(180),new TranslationalVelConstraint(20))
               */

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();

    }
}
