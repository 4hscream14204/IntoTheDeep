package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepAizah {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(620);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 15)
                .build();


        Action blueRight = myBot.getDrive().actionBuilder(new Pose2d(-14, 61, Math.toRadians(0)))
                .setTangent(Math.toRadians(270))
                //.afterTime(0.39, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroupPartOne(robotBase)))
                .splineToConstantHeading(new Vector2d(-2.0, 35.00), Math.toRadians(270.00), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-2.0, 27.00), Math.toRadians(270.00), new TranslationalVelConstraint(20))
                .waitSeconds(0.2)
               // .afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroupPartTwo(robotBase)))
                .waitSeconds(0.4)
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-2.0, 40), Math.toRadians(90), new TranslationalVelConstraint(20))
                //drive over to samples and move them to human player area
                .splineToSplineHeading(new Pose2d(-34.0, 46.00, Math.toRadians(90.00)), Math.toRadians(270.00), new TranslationalVelConstraint(25))
                //.setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-29.0, 16), Math.toRadians(270), new TranslationalVelConstraint(40))
                //.splineToLinearHeading(new Pose2d(-42.0, 12.00, Math.toRadians(90.00)), Math.toRadians(200.00), new TranslationalVelConstraint(50))
                // .setTangent(180)
                .splineToConstantHeading(new Vector2d(-45, 16), Math.toRadians(90),new TranslationalVelConstraint(40))
                .setTangent(Math.toRadians(90))
                //push into player area
                .splineToConstantHeading(new Vector2d(-45, 55), Math.toRadians(90),new TranslationalVelConstraint(40))
                .setTangent(Math.toRadians(270))
                //line up for second sample
                .splineToConstantHeading(new Vector2d(-45, 16), Math.toRadians(270),new TranslationalVelConstraint(40))
                // .setTangent(90)
                //push to human player
                .splineToConstantHeading(new Vector2d(-50, 16), Math.toRadians(90),new TranslationalVelConstraint(30))
                .splineToConstantHeading(new Vector2d(-50, 50), Math.toRadians(90),new TranslationalVelConstraint(50))
                .setTangent(Math.toRadians(270))
                //back up and wait
                .splineToLinearHeading(new Pose2d(-48.00, 42.00, Math.toRadians(180.00)), Math.toRadians(270.00), new TranslationalVelConstraint(40))
                .waitSeconds(0.5)
                // go forward and grab specimen
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-48, 60), Math.toRadians(90), new TranslationalVelConstraint(30))
                .splineToConstantHeading(new Vector2d(-48, 64), Math.toRadians(90), new TranslationalVelConstraint(30))
                //.afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new GrabSpecimenAndHangPosCommandGroup(robotBase)))
                .waitSeconds(0.2)
                //hang specimen
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(-4.50, 35.00, Math.toRadians(0.00)), Math.toRadians(270.00), new TranslationalVelConstraint(30))
                .waitSeconds(0.6)
                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-4.5, 27), Math.toRadians(270), new TranslationalVelConstraint(30))
                //.waitSeconds(0.2)
                //go back and grab next specimen
                //.afterTime(0.2, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroupPartTwo(robotBase)))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-48.00, 58.00, Math.toRadians(180.00)), Math.toRadians(90.00), new TranslationalVelConstraint(40))
                .splineToConstantHeading(new Vector2d(-48, 64), Math.toRadians(90), new TranslationalVelConstraint(30))
              //  .afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new GrabSpecimenAndHangPosCommandGroup(robotBase)))
                .waitSeconds(0.2)
                //hang specimen
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(-6.00, 35.00, Math.toRadians(0.00)), Math.toRadians(270.00), new TranslationalVelConstraint(30))
                .splineToConstantHeading(new Vector2d(-6, 27), Math.toRadians(270), new TranslationalVelConstraint(30))
                //.afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroupPartTwo(robotBase)))
               .waitSeconds(0.2)
                //park
               // .afterTime(0.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.HOME))))
              //  .afterTime(0.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.AUTOINIT))))
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-58.00, 60.66), Math.toRadians(120.00), new TranslationalVelConstraint(40))

                .build();
        myBot.runAction(blueRight);



        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}