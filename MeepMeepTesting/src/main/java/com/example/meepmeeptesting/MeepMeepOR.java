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
                        //.afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBER)))))
                       // .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOAVOIDENCE)))))
                       // .afterTime(0.1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP))))
                       // .afterTime(0.1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.BUCKETDROPOFF))))
                        .setTangent(Math.toRadians(270))
                        //Chamber avoidence
                        .splineToConstantHeading(new Vector2d(27, 16.2), Math.toRadians(0), new TranslationalVelConstraint(40))
                        //Chamber drop off
                        .splineToConstantHeading(new Vector2d(53, 16.2), Math.toRadians(0), new TranslationalVelConstraint(30))
                       // .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOPARK)))))
                        .splineToConstantHeading(new Vector2d(56, 10), Math.toRadians(270), new TranslationalVelConstraint(30))
                       // .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBERCLAMP)))))
                        //.afterTime(0.2, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.clawSubsystem.openClaw()))))
                        .waitSeconds(0.4)
                        .setTangent(Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(50, 18), Math.toRadians(180), new TranslationalVelConstraint(30))
                        .setTangent(45)
                      /* // .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME)))))
                       // .afterTime(.2, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.sweeperSubsystem.goToPosition(Sweeper.SweeperPosition.MIDDLE)))))
                        //First sample sweep pos
                        .splineToConstantHeading(new Vector2d(44, 18), Math.toRadians(180), new TranslationalVelConstraint(30))
                        .waitSeconds(0.1)
                        .setTangent(Math.toRadians(0))
                        //First sample drop off
                        .splineToConstantHeading(new Vector2d(6, 16.8), Math.toRadians(180), new TranslationalVelConstraint(60))
                        //.afterTime(0.0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.sweeperSubsystem.goToPosition(Sweeper.SweeperPosition.OUT)))))
                        //Second sample return
                        .setTangent(Math.toRadians(180))
                        .splineToConstantHeading(new Vector2d(52, 17.2), Math.toRadians(0), new TranslationalVelConstraint(30))
                        //.afterTime(0.0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.sweeperSubsystem.goToPosition(Sweeper.SweeperPosition.MIDDLE)))))
                        .waitSeconds(0.1)
                        .setTangent(Math.toRadians(0))
                        //Second sample drop off
                        .splineToConstantHeading(new Vector2d(6, 16.8), Math.toRadians(180), new TranslationalVelConstraint(60))
                        //.afterTime(0.0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.sweeperSubsystem.goToPosition(Sweeper.SweeperPosition.OUT)))))
                        /* //Third sample return
                         .splineToConstantHeading(new Vector2d(-64, 17.4), Math.toRadians(180), new TranslationalVelConstraint(30))
                         .afterTime(0.0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.sweeperSubsystem.goToPosition(Sweeper.SweeperPosition.MIDDLE)))))
                         .waitSeconds(0.4)
                         .setTangent(0)
                         //Third sample drop off
                         .splineToConstantHeading(new Vector2d(-7, 17), Math.toRadians(0), new TranslationalVelConstraint(40))
                         .afterTime(0.0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.sweeperSubsystem.goToPosition(Sweeper.SweeperPosition.MIDDLE)))))
                         */
                   /*     .splineToLinearHeading(new Pose2d(14.0, 16.00, Math.toRadians(270.00)), Math.toRadians(180.00), new TranslationalVelConstraint(50))
                        //.afterTime(0.0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.sweeperSubsystem.goToPosition(Sweeper.SweeperPosition.OUT)))))
                        .waitSeconds(0.2)
                        .setTangent(Math.toRadians(0))
                        .splineToConstantHeading(new Vector2d(-3, 16.8), Math.toRadians(0), new TranslationalVelConstraint(50))
                        //.afterTime(0.2, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.clawSubsystem.closeClaw()))))
                        //.afterTime(0.4, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBER)))))
                        .waitSeconds(0.2)
                        .setTangent(Math.toRadians(180))
                        .splineToSplineHeading(new Pose2d(20.0, 17.0, Math.toRadians(180.00)), Math.toRadians(0.00), new TranslationalVelConstraint(30))

                        //.afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOAVOIDENCE)))))
                        .splineToConstantHeading(new Vector2d(52, 18), Math.toRadians(0), new TranslationalVelConstraint(30))
                        //.afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOPARK)))))
                        .splineToConstantHeading(new Vector2d(56, 10), Math.toRadians(90), new TranslationalVelConstraint(30))
                        //.afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBERCLAMP)))))
                        //.afterTime(0.4, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.clawSubsystem.openClaw()))))
                        .setTangent(Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(53, 18), Math.toRadians(180), new TranslationalVelConstraint(30))
                        //.setTangent(45)
                      //  .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME)))))
                        .splineToSplineHeading(new Pose2d(13.0, 16.8, Math.toRadians(270.00)), Math.toRadians(180.00), new TranslationalVelConstraint(30))
                        .splineToConstantHeading(new Vector2d(-4, 16.8), Math.toRadians(180), new TranslationalVelConstraint(50))
                        //.afterTime(0.2, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.clawSubsystem.closeClaw()))))
                       // .afterTime(0.4, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBER)))))
                        .waitSeconds(0.2)
                        .setTangent(Math.toRadians(180))
                        .splineToSplineHeading(new Pose2d(20.0, 17.0, Math.toRadians(180.00)), Math.toRadians(0.00), new TranslationalVelConstraint(30))
                        //.afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOAVOIDENCE)))))
                        .splineToConstantHeading(new Vector2d(53, 18), Math.toRadians(0), new TranslationalVelConstraint(30))
                        //.afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOPARK)))))
                        .splineToConstantHeading(new Vector2d(58, 10), Math.toRadians(90), new TranslationalVelConstraint(30))
                        //.afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBERCLAMP)))))
                        //.afterTime(0.4, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.clawSubsystem.openClaw()))))
                        .setTangent(Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(53, 19), Math.toRadians(180), new TranslationalVelConstraint(40))
                        .setTangent(Math.toRadians(0))
                        //.afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME)))))
                        .splineToSplineHeading(new Pose2d(13.0, 16.8, Math.toRadians(270.00)), Math.toRadians(180.00), new TranslationalVelConstraint(40))
                        .splineToConstantHeading(new Vector2d(-5.0, 16.8), Math.toRadians(180), new TranslationalVelConstraint(50))
                       // .afterTime(0.2, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.clawSubsystem.closeClaw()))))
                       // .afterTime(0.4, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBER)))))
                        .waitSeconds(0.2)
                        .setTangent(Math.toRadians(180))
                        .splineToSplineHeading(new Pose2d(20.0, 19, Math.toRadians(180.00)), Math.toRadians(0.00), new TranslationalVelConstraint(40))
                       // .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOAVOIDENCE)))))
                       // .afterTime(0.5, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOPARK)))))
                        .splineToConstantHeading(new Vector2d(50, 18), Math.toRadians(0), new TranslationalVelConstraint(30))
                       // .afterTime(0.1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.BUCKETDROPOFF))))
                        .splineToConstantHeading(new Vector2d(55, 10), Math.toRadians(0), new TranslationalVelConstraint(30))
                       // .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBERCLAMP)))))
                       // .afterTime(0.0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PRESUBPICKUP)))))
                       // .afterTime(0.0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PRESUBPICKUP)))))
                       // .afterTime(0.4, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.clawSubsystem.openClaw()))))
                      //  .afterTime(0.1, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.HOME)))))
                        .waitSeconds(1)

                    */
                                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }

}
