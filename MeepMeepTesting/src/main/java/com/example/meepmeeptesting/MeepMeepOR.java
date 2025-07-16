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
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(0, 0, Math.toRadians(0)))
                .setTangent(90)
                //Chamber avoidence
                .splineToConstantHeading(new Vector2d(-27, 17.3), Math.toRadians(180), new TranslationalVelConstraint(30))
                //Chamber drop off
                .splineToConstantHeading(new Vector2d(-56, 17.3), Math.toRadians(180), new TranslationalVelConstraint(30))
                .splineToConstantHeading(new Vector2d(-56, 15), Math.toRadians(180), new TranslationalVelConstraint(20))
                .setTangent(45)
                //First sample sweep pos
                .splineToConstantHeading(new Vector2d(-42, 17.3), Math.toRadians(0), new TranslationalVelConstraint(30))
                .setTangent(0)
                //First sample drop off
                .splineToConstantHeading(new Vector2d(-10, 17.5), Math.toRadians(180), new TranslationalVelConstraint(30))
                // .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.sweeperSubsystem.goToPosition(Sweeper.SweeperPosition.HOME)))))
                //Second sample return
                .splineToConstantHeading(new Vector2d(-52, 17.3), Math.toRadians(0), new TranslationalVelConstraint(20))
               // .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.sweeperSubsystem.goToPosition(Sweeper.SweeperPosition.OUT)))))
                .setTangent(0)
                //Second sample drop off
                .splineToConstantHeading(new Vector2d(-10, 17.5), Math.toRadians(180), new TranslationalVelConstraint(30))
                //Third sample return
                .splineToConstantHeading(new Vector2d(-62, 17.5), Math.toRadians(0), new TranslationalVelConstraint(20))
              .setTangent(0)
                //Third sample drop off
                .splineToConstantHeading(new Vector2d(-10, 17.5), Math.toRadians(180), new TranslationalVelConstraint(30))
                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }

}
