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
               // .afterTime(0.39, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroupPartOne(robotBase)))
                .splineToConstantHeading(new Vector2d(-2.0, 35.00), Math.toRadians(270.00), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-2.0, 30.00), Math.toRadians(270.00), new TranslationalVelConstraint(20))
                .waitSeconds(0.2)
                //.afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroupPartTwo(robotBase)))
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-2.0, 40), Math.toRadians(90), new TranslationalVelConstraint(20))
                //drive over to samples and move them to human player area
                .splineToLinearHeading(new Pose2d(-38.0, 40.00, Math.toRadians(180.00)), Math.toRadians(180.00), new TranslationalVelConstraint(30))
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(-38.0, 12.00, Math.toRadians(180.00)), Math.toRadians(200.00), new TranslationalVelConstraint(30))
                //.splineToLinearHeading(new Pose2d(-47.0, 12.00, Math.toRadians(180.00)), Math.toRadians(200.00), new TranslationalVelConstraint(30))
                .setTangent(180)
                .splineToConstantHeading(new Vector2d(-47, 12), Math.toRadians(90),new TranslationalVelConstraint(30))
                .setTangent(Math.toRadians(90))
                //push into player area
                .splineToConstantHeading(new Vector2d(-47, 55), Math.toRadians(90),new TranslationalVelConstraint(30))
                .setTangent(270)
                //line up for second sample
              //  .splineToLinearHeading(new Pose2d(-43.0, 12.00, Math.toRadians(130.00)), Math.toRadians(180.00), new TranslationalVelConstraint(30))
                .splineToConstantHeading(new Vector2d(-46, 12), Math.toRadians(90),new TranslationalVelConstraint(30))
                .setTangent(180)
                .splineToConstantHeading(new Vector2d(-56, 12), Math.toRadians(90),new TranslationalVelConstraint(30))
                //.splineToLinearHeading(new Pose2d(-56.00, 12.00, Math.toRadians(130.00)), Math.toRadians(200.00), new TranslationalVelConstraint(30))
                .setTangent(90)
                //push to human player
                .splineToConstantHeading(new Vector2d(-56, 55), Math.toRadians(90),new TranslationalVelConstraint(30))
                //.splineToLinearHeading(new Pose2d(-42.00, 55.00, Math.toRadians(90.00)), Math.toRadians(120.00), new TranslationalVelConstraint(30))
                .setTangent(270)
                //back up and wait
                .splineToLinearHeading(new Pose2d(-44.00, 42.00, Math.toRadians(180.00)), Math.toRadians(270.00), new TranslationalVelConstraint(40))
                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }

}
