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
import org.firstinspires.ftc.teamcode.commands.AutoInitCommandGroup;
import org.firstinspires.ftc.teamcode.commands.ExtensionHomeCommandGroup;
import org.firstinspires.ftc.teamcode.commands.GrabSpecimenAndHangPosCommandGroup;
import org.firstinspires.ftc.teamcode.commands.ShoulderHomeCommandGroup;
import org.firstinspires.ftc.teamcode.commands.SpecimenPickupAutoCommandGroup;
import org.firstinspires.ftc.teamcode.commands.HangSpecimenAutoCommandGroup;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

import java.util.List;



//@Disabled
@Autonomous (name = "RedRightx5")
public class RedRightx5 extends OpMode {
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
        //robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.ZERO);
        //robotBase.clawSubsystem.closeClaw();
        //robotBase.elbowSubsystem.enmElbowPosition = Elbow.ElbowPosition.HOME;
        //robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOPARK);
        CommandScheduler.getInstance().schedule(new AutoInitCommandGroup(robotBase));
        baseController.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new InstantCommand(
                        ()-> waitSec++
                ));

        baseController.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(new InstantCommand(
                        ()-> waitSec--
                ));

        baseController.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new InstantCommand(()-> DataStorage.strategy = ITDCrabEnums.Strategy.SPECIMENSTOCKPILE));

        baseController.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(new InstantCommand(()-> DataStorage.strategy = ITDCrabEnums.Strategy.SPECIMENBASICCYCLE));

        baseController.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(new InstantCommand(()-> DataStorage.strategy = ITDCrabEnums.Strategy.BUCKETBASICCYCLE));

        blueRightx4Action = robotBase.drive.actionBuilder(startPose)

                // hang preload
                .setTangent(Math.toRadians(270))
                .afterTime(0.0, ()-> CommandScheduler.getInstance().schedule(new SpecimenPickupAutoCommandGroup(robotBase)))
                .splineToConstantHeading(new Vector2d(-2.0, 28.00), Math.toRadians(270.00), new TranslationalVelConstraint(40))

                //.waitSeconds(0.1)
                .afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroup(robotBase)))
                .waitSeconds(0.1)

                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-2.0, 36), Math.toRadians(90), new TranslationalVelConstraint(35))

                //drive over to samples and move them to human player area
                .splineToSplineHeading(new Pose2d(-32, 36.00,Math.toRadians(180)), Math.toRadians(270.00), new TranslationalVelConstraint(30))
                .afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new ShoulderHomeCommandGroup(robotBase.shoulderSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem)))
                .afterTime(0.1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.AUTO))))
                .afterTime(0.1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.HOME))))
                .splineToConstantHeading(new Vector2d(-30, 15), Math.toRadians(270), new TranslationalVelConstraint(40))
                .splineToConstantHeading(new Vector2d(-38, 15), Math.toRadians(90),new TranslationalVelConstraint(28))
                .setTangent(Math.toRadians(90))
                //push into player area
                .splineToConstantHeading(new Vector2d(-36, 46), Math.toRadians(90),new TranslationalVelConstraint(40))
                //.setTangent(Math.toRadians(270))
                //line up for second sample
                .splineToConstantHeading(new Vector2d(-36, 15), Math.toRadians(270),new TranslationalVelConstraint(40))
                //push to human player
                .splineToConstantHeading(new Vector2d(-46, 15), Math.toRadians(90),new TranslationalVelConstraint(28))
                .splineToConstantHeading(new Vector2d(-46, 46), Math.toRadians(90),new TranslationalVelConstraint(40))
                //Line up for third sample
                .splineToConstantHeading(new Vector2d(-46 , 15), Math.toRadians(270),new TranslationalVelConstraint(40))
                .splineToConstantHeading(new Vector2d(-57, 15), Math.toRadians(90),new TranslationalVelConstraint(28))
                .afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.TOGGLE))))
                .afterTime(0.2, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP))))
                .afterTime(0.2, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP))))
                .splineToConstantHeading(new Vector2d(-57, 46), Math.toRadians(90),new TranslationalVelConstraint(40))
                //.setTangent(Math.toRadians(270))

                // go forward and grab specimen
                // .splineToSplineHeading(new Pose2d(-47, 50.00, Math.toRadians(180.00)), Math.toRadians(90), new TranslationalVelConstraint(35))
                .splineToConstantHeading(new Vector2d(-47.00, 50), Math.toRadians(90), new TranslationalVelConstraint(35))
                .splineToConstantHeading(new Vector2d(-47.00, 65), Math.toRadians(90), new TranslationalVelConstraint(35))
                .afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new GrabSpecimenAndHangPosCommandGroup(robotBase)))
                .waitSeconds(0.01)

                //hang specimen
                .setTangent(Math.toRadians(315))
                .splineToSplineHeading(new Pose2d(-3.5, 27.00, Math.toRadians(0.00)), Math.toRadians(300), new TranslationalVelConstraint(40))
                .afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroup(robotBase)))
                .waitSeconds(0.1)
                //go and grab another specimen
                .setTangent(Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(-41.0, 64, Math.toRadians(180.00)), Math.toRadians(135), new TranslationalVelConstraint(40))
                .afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new GrabSpecimenAndHangPosCommandGroup(robotBase)))
                .waitSeconds(0.01)
                //hang specimen
                .setTangent(Math.toRadians(315))
                .splineToSplineHeading(new Pose2d(-3.5, 28, Math.toRadians(358.00)), Math.toRadians(300), new TranslationalVelConstraint(40))

                .afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroup(robotBase)))
                .waitSeconds(0.1)
                //go and grab another specimen
                .setTangent(Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(-41.0, 64, Math.toRadians(180.00)), Math.toRadians(135), new TranslationalVelConstraint(40))
                .afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new GrabSpecimenAndHangPosCommandGroup(robotBase)))
                .waitSeconds(0.01)
                //hang specimen
                .setTangent(Math.toRadians(315))
                .splineToSplineHeading(new Pose2d(-3.5, 28, Math.toRadians(358.00)), Math.toRadians(300), new TranslationalVelConstraint(40))
                .afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroup(robotBase)))
                .waitSeconds(0.1)
                //go and grab another specimen
                .setTangent(Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(-41, 64, Math.toRadians(180.00)), Math.toRadians(135), new TranslationalVelConstraint(40))
                .afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new GrabSpecimenAndHangPosCommandGroup(robotBase)))
                .waitSeconds(0.01)
                //hang specimen
                .setTangent(Math.toRadians(315))
                .splineToSplineHeading(new Pose2d(-3.5, 28, Math.toRadians(358.00)), Math.toRadians(300), new TranslationalVelConstraint(40))
                .afterTime(0.0, ()->CommandScheduler.getInstance().schedule(new HangSpecimenAutoCommandGroup(robotBase)))
                .waitSeconds(0.4)
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.HOME))))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PRESUBPICKUP))))
                //.afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.clawSubsystem.closeClaw())))
                .waitSeconds(0.1)

                .build();

        robotBase.alliance = ITDCrabEnums.EnmAlliance.RED;

        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
            module.clearBulkCache();
        }


    }@Override
    public void init_loop() {
        CommandScheduler.getInstance().run();
        telemetry.addData("Wait time", waitSec);
        telemetry.addData("Shoulder Position", robotBase.shoulderSubsystem.shoulderGetPosition());
        telemetry.addData("Shoulder Target Position", robotBase.shoulderSubsystem.dcShoulderMotorLeft.getTargetPosition());
        telemetry.addData("Shoulder Limit Switch", robotBase.shoulderSubsystem.isShoulderHome());
        telemetry.addData("Shoulder Power", robotBase.shoulderSubsystem.getPower());
        telemetry.addData("Strategy: ", DataStorage.strategy);

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
        //  robotBase.drive.updatePoseEstimate();
        //  telemetry.addData("x", robotBase.drive.pose.position.x);
        //  telemetry.addData("y", robotBase.drive.pose.position.y);
        //  telemetry.addData("heading (deg)", Math.toDegrees(robotBase.drive.pose.heading.toDouble()));
        //  telemetry.addData("Feild Position y")
        // telemetry.addData("Shoulder Position", robotBase.shoulderSubsystem.shoulderGetPosition());
        // telemetry.addData("Shoulder Target Position", robotBase.shoulderSubsystem.dcShoulderMotorLeft.getTargetPosition());
        // telemetry.addData("Shoulder Switch", robotBase.shoulderSubsystem.isShoulderHome());
        blueRightx4Action.run(telemetryPacket);
    }

    @Override
    public void stop() {
        robotBase.drive.updatePoseEstimate();
        DataStorage.alliance = robotBase.alliance;
        DataStorage.dblIMUFinalHeadingRad = robotBase.drive.otos.getPosition().h;

    }
}