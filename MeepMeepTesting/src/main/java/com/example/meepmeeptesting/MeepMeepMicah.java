package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

;

//import org.firstinspires.ftc.teamcode.base.DataStorage;
//import org.firstinspires.ftc.teamcode.base.ITDCrabEnums;
//import org.firstinspires.ftc.teamcode.base.RobotBase;
//import org.firstinspires.ftc.teamcode.subsystems.Elbow;
//import org.firstinspires.ftc.teamcode.subsystems.Extension;
//import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
//import org.firstinspires.ftc.teamcode.subsystems.Wrist;


public class MeepMeepMicah {
    public class HangSpecimenAutoCommandGroupPartOne extends SequentialCommandGroup {
   // public static void main(String[] args);

    public GamepadEx armController;
    public GamepadEx baseController;
    public int waitSec = 0;
    public Action waitAction;
    public Action blueRightx4Action;{
        MeepMeep meepMeep = new MeepMeep(720);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)


                .build();
        Object robotBase = new Object()
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(14, 61, Math.toRadians(180)))
                .waitSeconds(2)
                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(8, 34), Math.toRadians(270), new TranslationalVelConstraint(20))
                        //.afterTime(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.NEWHIGHCHAMBERCLAMP))
                        //.afterTime(new InstantCommand(()->robotBase.clawSubsystem.openClaw()))
                        //.afterTime(new WaitCommand(500))
                        //.afterTime(new ExtensionHomeCommandGroup(robotBase.extensionSubsystem, robotBase.elbowSubsystem))
                        //.afterTime(new WaitUntilCommand(()->robotBase.extensionSubsystem.isExtensionHome()))
                        //.afterTime(new ShoulderHomeCommandGroup(robotBase.shoulderSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(40, 33, Math.toRadians(230)), Math.toRadians(310))
                        //.afterTime(new InstantCommand(()->robotBase.
                .setTangent(Math.toRadians(55))

            //  .splineToConstantHeading(new Vector2d(40.12, 33.44), Math.toRadians(-12.77))
                .splineToLinearHeading(new Pose2d(50, 56, Math.toRadians(-42)), Math.toRadians(59))
                .setTangent(Math.toRadians(250))
                .splineToLinearHeading(new Pose2d(52, 29, Math.toRadians(180)), Math.toRadians(-87), new TranslationalVelConstraint(20))
                .setTangent(Math.toRadians(45))
                .splineToLinearHeading(new Pose2d(50, 56, Math.toRadians(317)), Math.toRadians(55), new TranslationalVelConstraint(20))
                .setTangent(Math.toRadians(270))
               // .splineTo(new Vector2d(67.55, 56.43), Math.toRadians(254.77))
                .splineToLinearHeading(new Pose2d(54, 31, Math.toRadians(180)), Math.toRadians(45), new TranslationalVelConstraint(20))
                .setTangent(Math.toRadians(45))
                .splineToLinearHeading(new Pose2d(57, 50, Math.toRadians(320)), Math.toRadians(45), new TranslationalVelConstraint(20))
                .setTangent(Math.toRadians(270))
                .splineTo(new Vector2d(50, 19), Math.toRadians(220), new TranslationalVelConstraint(20))
                .splineTo(new Vector2d(24, 12), Math.toRadians(180), new TranslationalVelConstraint(20))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
