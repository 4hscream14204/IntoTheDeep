package org.firstinspires.ftc.teamcode.opmode.auto;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.ITDCrabEnums;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.GrabSpecimenAndHangPosCommandGroup;
import org.firstinspires.ftc.teamcode.commands.SpecimenPickupAutoCommandGroup;
import org.firstinspires.ftc.teamcode.commands.HangSpecimenAutoCommandGroup;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

import java.util.List;



//@Disabled
@Autonomous (name = "BlueRightx5")
public class BlueRightx5 extends OpMode {
    public TelemetryPacket telemetryPacket;

    public Pose2d startPose;
    public RobotBase robotBase;
    public GamepadEx armController;
    public GamepadEx baseController;
    public int waitSec = 0;
    public Action waitAction;
    public Action blueRightx4Action;


    @Override
    public void init() {
        startPose = new Pose2d(-14, 61, Math.toRadians(0));
        robotBase =new RobotBase(hardwareMap);
        armController = new GamepadEx(gamepad2);
        baseController = new GamepadEx(gamepad1);
        CommandScheduler.getInstance().reset();
        CommandScheduler.getInstance().cancelAll();
        CommandScheduler.getInstance().clearButtons();
        robotBase.drive.pose = startPose;
        telemetryPacket = new TelemetryPacket();
        //robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.INIT);
        robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.ZERO);
        robotBase.clawSubsystem.closeClaw();
        //robotBase.elbowSubsystem.enmElbowPosition = Elbow.ElbowPosition.HOME;
        robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOPARK);

        baseController.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new InstantCommand(
                        ()-> waitSec++
                ));

        baseController.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(new InstantCommand(
                        ()-> waitSec--
                ));


        blueRightx4Action = robotBase.drive.actionBuilder(startPose)

                // hang preload
                .setTangent(Math.toRadians(270))
                //.afterTime(0.0, ()-> CommandScheduler.getInstance().schedule(new SpecimenPickupAutoCommandGroup(robotBase)))
                .splineToConstantHeading(new Vector2d(-2.0, 28.00), Math.toRadians(270.00), new TranslationalVelConstraint(30))
                .waitSeconds(0.2)
                //.afterTime(0.2, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroup(robotBase)))
                .waitSeconds(0.5)

                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-2.0, 32), Math.toRadians(90), new TranslationalVelConstraint(35))
                //drive over to samples and move them to human player area
                .splineToSplineHeading(new Pose2d(-28.0, 34.00,Math.toRadians(180.00)), Math.toRadians(270.00), new TranslationalVelConstraint(30))
                .splineToConstantHeading(new Vector2d(-28.0, 17), Math.toRadians(270), new TranslationalVelConstraint(35))
                .splineToConstantHeading(new Vector2d(-38, 17), Math.toRadians(90),new TranslationalVelConstraint(30))
                .setTangent(Math.toRadians(90))
                //push into player area
                .splineToConstantHeading(new Vector2d(-38, 54), Math.toRadians(90),new TranslationalVelConstraint(35))
                .setTangent(Math.toRadians(270))
                //line up for second sample
                .splineToConstantHeading(new Vector2d(-38, 17), Math.toRadians(270),new TranslationalVelConstraint(35))
                //push to human player
                .splineToConstantHeading(new Vector2d(-48, 17), Math.toRadians(90),new TranslationalVelConstraint(30))
                .splineToConstantHeading(new Vector2d(-48, 48), Math.toRadians(90),new TranslationalVelConstraint(35))

                .splineToConstantHeading(new Vector2d(-48, 17), Math.toRadians(270),new TranslationalVelConstraint(30))
                .splineToConstantHeading(new Vector2d(-56, 17), Math.toRadians(90),new TranslationalVelConstraint(30))
                .splineToConstantHeading(new Vector2d(-56, 50), Math.toRadians(90),new TranslationalVelConstraint(30))
                /*      .setTangent(Math.toRadians(270))
                           // go forward and grab specimen
                           .splineToConstantHeading(new Vector2d(-45.00, 50), Math.toRadians(90), new TranslationalVelConstraint(35))
                           .splineToConstantHeading(new Vector2d(-45.00, 62), Math.toRadians(90), new TranslationalVelConstraint(25))
                           //.afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new GrabSpecimenAndHangPosCommandGroup(robotBase)))
                           .waitSeconds(0.2)

                           //hang specimen
                           .setTangent(Math.toRadians(315))
                           .splineToSplineHeading(new Pose2d(-4, 28.00, Math.toRadians(0.00)), Math.toRadians(300), new TranslationalVelConstraint(35))
                           //.afterTime(0.2, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroup(robotBase)))
                           .waitSeconds(0.4)
                           //go and grab another specimen
                           .setTangent(Math.toRadians(90))
                           .splineToSplineHeading(new Pose2d(-44.0, 63.00, Math.toRadians(180.00)), Math.toRadians(135), new TranslationalVelConstraint(35))
                           //.afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new GrabSpecimenAndHangPosCommandGroup(robotBase)))
                           .waitSeconds(0.2)
                           //hang specimen
                           .setTangent(Math.toRadians(315))
                           .splineToSplineHeading(new Pose2d(-4, 28.00, Math.toRadians(0.00)), Math.toRadians(300), new TranslationalVelConstraint(35))

                           //.afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroup(robotBase)))
                           .waitSeconds(0.4)
                           //go and grab another specimen
                           .setTangent(Math.toRadians(90))
                           .splineToSplineHeading(new Pose2d(-45.0, 63.00, Math.toRadians(180.00)), Math.toRadians(135), new TranslationalVelConstraint(25))
                           //.afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new GrabSpecimenAndHangPosCommandGroup(robotBase)))
                           .waitSeconds(0.2)
                           //hang specimen
                           .setTangent(Math.toRadians(315))
                           .splineToSplineHeading(new Pose2d(-4, 28.00, Math.toRadians(0.00)), Math.toRadians(300), new TranslationalVelConstraint(35))

                           //.afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroup(robotBase)))
                           .waitSeconds(0.4)
                           //park
                           .setTangent(Math.toRadians(90))
                           .splineToLinearHeading(new Pose2d(-54.00, 54, Math.toRadians(180)), Math.toRadians(180.00), new TranslationalVelConstraint(50))
                           //.afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.HOME))))
                           //.afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PRESUBPICKUP))))
                           //.afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.clawSubsystem.closeClaw())))
               */

                .build();

        robotBase.alliance = ITDCrabEnums.EnmAlliance.BLUE;


    }@Override
    public void init_loop() {
        CommandScheduler.getInstance().run();
        telemetry.addData("Wait time", waitSec);
        telemetry.addData("Shoulder Position", robotBase.shoulderSubsystem.shoulderGetPosition());
        telemetry.addData("Shoulder Target Position", robotBase.shoulderSubsystem.dcShoulderMotorLeft.getTargetPosition());

    }

    @Override
    public void start() {
        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
            module.clearBulkCache();
        }
        CommandScheduler.getInstance().reset();
        CommandScheduler.getInstance().cancelAll();
        CommandScheduler.getInstance().clearButtons();
        if (waitSec > 0) {
            waitAction = robotBase.drive.actionBuilder(startPose)
                    .waitSeconds(waitSec)
                    .build();

            Actions.runBlocking(waitAction);
        }

    }

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
        robotBase.drive.updatePoseEstimate();
        telemetry.addData("x", robotBase.drive.pose.position.x);
        telemetry.addData("y", robotBase.drive.pose.position.y);
        telemetry.addData("heading (deg)", Math.toDegrees(robotBase.drive.pose.heading.toDouble()));
        //  telemetry.addData("Feild Position y")
        telemetry.addData("Shoulder Position", robotBase.shoulderSubsystem.shoulderGetPosition());
        telemetry.addData("Shoulder Target Position", robotBase.shoulderSubsystem.dcShoulderMotorLeft.getTargetPosition());
        blueRightx4Action.run(telemetryPacket);
    }

    @Override
    public void stop() {
        robotBase.drive.updatePoseEstimate();
        DataStorage.alliance = robotBase.alliance;
        DataStorage.dblIMUFinalHeadingRad = robotBase.drive.otos.getPosition().h;

    }
}