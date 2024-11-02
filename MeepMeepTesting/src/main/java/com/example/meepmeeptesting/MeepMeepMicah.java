package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepMicah {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(750);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        Action testAct1 = myBot.getDrive().actionBuilder(new Pose2d(-14, 61, Math.toRadians(0)))
                .setTangent(Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(-20, 40), Math.toRadians(-90.00))
                .splineToConstantHeading(new Vector2d(-20, 33), Math.toRadians(-90.00), new TranslationalVelConstraint(20))
                //.setReversed(true)
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-36, 34), Math.toRadians(-90.00), new TranslationalVelConstraint(20))
                //.setReversed(false)
                .splineToConstantHeading(new Vector2d(-36, 13), Math.toRadians(-90.00))
                //Curve behind sample
                .splineToConstantHeading(new Vector2d(-47, 14), Math.toRadians(90.00), new TranslationalVelConstraint(20))
                //.setReversed(true)
                .splineToConstantHeading(new Vector2d(-47, 31), Math.toRadians(90.00))
                .splineToConstantHeading(new Vector2d(-47, 57), Math.toRadians(90.00))
                .build();
        // Blue right hang & park
        Action testAct2 = myBot.getDrive().actionBuilder(new Pose2d(-14, 61, Math.toRadians(0)))
                .setTangent(Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(-20, 45), Math.toRadians(-90.00))
                .splineToConstantHeading(new Vector2d(-20, 33), Math.toRadians(-90.00), new TranslationalVelConstraint(20))
                .setTangent(Math.toRadians(90))
                //.setReversed(true)
                .splineToConstantHeading(new Vector2d(-54, 59), Math.toRadians(180.00))
                .splineToConstantHeading(new Vector2d(-57, 59), Math.toRadians(180.00), new TranslationalVelConstraint(20))
                //.setReversed(false)
                .build();


        // Blue left hang & park
        Action testAct3 = myBot.getDrive().actionBuilder(new Pose2d(14, 61, Math.toRadians(0)))
                .setTangent(Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(12, 38), Math.toRadians(-90.00))
                .splineToConstantHeading(new Vector2d(12, 34), Math.toRadians(-90.00), new TranslationalVelConstraint(15))
                .setTangent(Math.toRadians(90))
               // .setReversed(true)
                .splineToConstantHeading(new Vector2d(-55, 60), Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-60, 60), Math.toRadians(180.00), new TranslationalVelConstraint(15))
                //.setReversed(false)
                .build();




        myBot.runAction(testAct2);



        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}