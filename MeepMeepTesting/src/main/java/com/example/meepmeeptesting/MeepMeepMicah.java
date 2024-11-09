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

        Action blueRight = myBot.getDrive().actionBuilder(new Pose2d(-14, 61, Math.toRadians(0)))
                //.afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberClosed())))
                //.afterTime(0.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.AUTOHIGHCHAMBERSTART))))
                .waitSeconds(0.25)
                .setTangent(Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(-13, 45), Math.toRadians(-90.00), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-13, 32), Math.toRadians(-90.00), new TranslationalVelConstraint(20))
                //.afterTime(0.25, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.AUTOHIGHCHAMBERCLAMP))))
                //.afterTime(2, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberOpen())))
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-36, 34), Math.toRadians(-90.00), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-36, 13), Math.toRadians(-90.00))
                //Curve behind sample
                .splineToConstantHeading(new Vector2d(-47, 14), Math.toRadians(90.00), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-47, 31), Math.toRadians(90.00), new TranslationalVelConstraint(30))
                .splineToConstantHeading(new Vector2d(-47, 57), Math.toRadians(90.00), new TranslationalVelConstraint(30))
                .build();


        // Blue right hang & park
        Action blueRighthp = myBot.getDrive().actionBuilder(new Pose2d(-14, 61, Math.toRadians(180)))
                .setTangent(Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(-13, 45), Math.toRadians(90.00), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-13, 33), Math.toRadians(90.00), new TranslationalVelConstraint(20))
                .waitSeconds(5)
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-40, 59), Math.toRadians(0.00))
                .splineToConstantHeading(new Vector2d(-52, 59), Math.toRadians(0.00), new TranslationalVelConstraint(20))
                .build();


        // Blue left hang & park
      /*  Action blueLefthp = myBot.getDrive().actionBuilder(new Pose2d(14, 61, Math.toRadians(180)))
                .setTangent(Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(13, 45), Math.toRadians(90.00), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(13, 33), Math.toRadians(90.00), new TranslationalVelConstraint(20))
                .waitSeconds(5)
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-20, 62), Math.toRadians(-180.00))
                .splineToConstantHeading(new Vector2d(-16, 62), Math.toRadians(-180.00), new TranslationalVelConstraint(20))
                .build();

       */
        Action blueRight2drops = myBot.getDrive().actionBuilder(new Pose2d(-14, 61, Math.toRadians(180)))
                //.afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberClosed())))
                //.afterTime(0.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.AUTOHIGHCHAMBERSTART))))
                .waitSeconds(0.25)
                .setTangent(Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(-13, 45), Math.toRadians(-90.00), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-13, 32), Math.toRadians(-90.00), new TranslationalVelConstraint(20))
                //.afterTime(0.25, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.AUTOHIGHCHAMBERCLAMP))))
                //.afterTime(2, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberOpen())))
                .waitSeconds(1)
               // .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.HOME))))
                .setTangent(Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(-40, 59, Math.toRadians(90)), Math.toRadians(180.00))
                .splineToConstantHeading(new Vector2d(-56, 59), Math.toRadians(180.00), new TranslationalVelConstraint(20))
                //.afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberClosed())))
                //.afterTime(0.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.AUTOHIGHCHAMBERSTART))))
                .waitSeconds(1)
                .setTangent(0)
                .splineToSplineHeading(new Pose2d(-10, 40, Math.toRadians(180)), Math.toRadians(-90.00), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-10, 32), Math.toRadians(-90.00), new TranslationalVelConstraint(20))
                //.afterTime(0.25, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.AUTOHIGHCHAMBERCLAMP))))
                //.afterTime(2, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberOpen())))
                .waitSeconds(1)
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-56, 59), Math.toRadians(180.00), new TranslationalVelConstraint(20))
                // .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.HOME))))
                .build();

               //RedRight
        Action redRight = myBot.getDrive().actionBuilder(new Pose2d(14, -61, Math.toRadians(0)))
                //.afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberClosed())))
               // .afterTime(0.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.AUTOHIGHCHAMBERSTART))))
                .waitSeconds(0.25)
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(13, -45), Math.toRadians(90.00), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(13, -32), Math.toRadians(90.00), new TranslationalVelConstraint(20))
               // .afterTime(0.25, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.AUTOHIGHCHAMBERCLAMP))))
               // .afterTime(2, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberOpen())))
                .waitSeconds(2)
                .setTangent(Math.toRadians(-90))
                .splineToSplineHeading(new Pose2d(40, -67, Math.toRadians(-90)), Math.toRadians(0))
               // .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.PICKUP))))
                .splineToConstantHeading(new Vector2d(55.5, -67), Math.toRadians(0), new TranslationalVelConstraint(20))
                //.afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberClosed())))
                //.afterTime(0.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.HIGHCHAMBERSTART))))
                .waitSeconds(1)
                .setTangent(Math.toRadians(180))
                .splineToSplineHeading(new Pose2d(10, -40, Math.toRadians(0)), Math.toRadians(90.00), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(10, -33.5), Math.toRadians(90.00), new TranslationalVelConstraint(20))
              //  .afterTime(0.25, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.HIGHCHAMBERCLAMP))))
                //.afterTime(2, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.specimenGrabberSubsystem.grabberOpen())))
                .waitSeconds(1)
                .setTangent(Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(56, -59), Math.toRadians(0), new TranslationalVelConstraint(20))
               // .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.liftSubsystem.goToPosition(Lift.LiftPosition.HOME))))
                .build();

        // Blue right hang & park
        Action redLeft = myBot.getDrive().actionBuilder(new Pose2d(14, -61, Math.toRadians(0)))
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(20, -45), Math.toRadians(90.00))
                .splineToConstantHeading(new Vector2d(20, -33), Math.toRadians(90.00), new TranslationalVelConstraint(20))
                .setTangent(Math.toRadians(-90))
                //.setReversed(true)
                .splineToConstantHeading(new Vector2d(54, -59), Math.toRadians(-180.00))
                .splineToConstantHeading(new Vector2d(57, -59), Math.toRadians(-180.00), new TranslationalVelConstraint(20))
                //.setReversed(false)
                .build();

        Action redHangPark = myBot.getDrive().actionBuilder(new Pose2d(-20, -61, Math.toRadians(0)))
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-16, -45), Math.toRadians(90.00), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-16, -33), Math.toRadians(90.00), new TranslationalVelConstraint(20))
                .waitSeconds(5)
                .setTangent(Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(20, -62), Math.toRadians(-180.00))
                .splineToConstantHeading(new Vector2d(16, -62), Math.toRadians(-180.00), new TranslationalVelConstraint(20))
                .build();



        myBot.runAction(redRight);



        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}