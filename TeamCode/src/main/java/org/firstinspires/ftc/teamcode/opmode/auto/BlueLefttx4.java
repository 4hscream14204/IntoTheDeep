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
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.ITDCrabEnums;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.AutoInitCommandGroup;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

@Autonomous(name = "BlueBucketx4")
public class BlueLefttx4 extends OpMode{
    public TelemetryPacket telemetryPacket;

    public Pose2d startPose;

    public RobotBase robotBase;
    public GamepadEx armController;
    public GamepadEx baseController;
    public int waitSec = 0;
    public Action waitAction;
    public Action blueLeftAction;


    @Override
    public void init() {
        startPose = new Pose2d(14, 61, Math.toRadians(0));
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

        blueLeftAction = robotBase.drive.actionBuilder(startPose)
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.clawSubsystem.openClaw())))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.TOGGLE))))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.MAXSHOULDERUPPOSITION))))
              //   .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.MAX))))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP))))
                .afterTime(0.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.DROPOFF))))
                .afterTime(0.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.BUCKETDROPOFF))))
                .setTangent(Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(57, 58,Math.toRadians(135.00)), Math.toRadians(45.00))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.OPEN))))
                .afterTime(0.2, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.intakeOuttake())))
                .afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.intakeStop())))
                .afterTime(1.3, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.CLOSED))))
                .afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP))))
                .afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP))))
                .afterTime(1.5, ()-> CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME))))
                .waitSeconds(1)

                .setTangent(Math.toRadians(225))
                .splineToLinearHeading(new Pose2d(47.5,47,Math.toRadians(180)),Math.toRadians(180),new TranslationalVelConstraint(30))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.intakeSubsystem.intakeSpeed(1)))))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.HOME))))
                .afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.LOWBUCKET))))
                .afterTime(2, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME))))
                .afterTime(2.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.intakeStop())))
                .afterTime(2.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.TOGGLE))))
                .afterTime(2.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHBUCKET))))
                .waitSeconds(3)
                .splineToLinearHeading(new Pose2d(57,58,Math.toRadians(135)),Math.toRadians(45),new TranslationalVelConstraint(30))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.BUCKETDROPOFF))))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.DROPOFF))))
                .afterTime(0.3, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.OPEN))))
                .afterTime(0.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.intakeOuttake())))
                .afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.intakeStop())))
                .afterTime(1.3, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.CLOSED))))
                .afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP))))
                .afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP))))
                .afterTime(1.5, ()-> CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME))))
                .waitSeconds(1)

                .setTangent(Math.toRadians(225))
                .splineToLinearHeading(new Pose2d(56,47,Math.toRadians(180)),Math.toRadians(180),new TranslationalVelConstraint(30))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.intakeSubsystem.intakeSpeed(1)))))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.HOME))))
                .afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.LOWBUCKET))))
                .afterTime(2, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME))))
                .afterTime(2.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.intakeStop())))
                .afterTime(2.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.TOGGLE))))
                .afterTime(2.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHBUCKET))))
                .waitSeconds(3)
                .splineToLinearHeading(new Pose2d(57,58,Math.toRadians(135)),Math.toRadians(45),new TranslationalVelConstraint(30))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.BUCKETDROPOFF))))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.DROPOFF))))
                .afterTime(0.3, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.OPEN))))
                .afterTime(0.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.intakeOuttake())))
                .afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.intakeStop())))
                .afterTime(1.3, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.CLOSED))))
                .afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP))))
                .afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP))))
                .afterTime(1.5, ()-> CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME))))
                .waitSeconds(1)

                .setTangent(Math.toRadians(225))
                .splineToLinearHeading(new Pose2d(52,44,Math.toRadians(-143)),Math.toRadians(-143),new TranslationalVelConstraint(30))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.intakeSubsystem.intakeSpeed(1)))))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.HOME))))
                .afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBERCLAMP))))
                .afterTime(2, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME))))
                .afterTime(2.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.intakeStop())))
                .afterTime(2.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.TOGGLE))))
                .afterTime(2.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHBUCKET))))
                .waitSeconds(3)
                .splineToLinearHeading(new Pose2d(57,58,Math.toRadians(135)),Math.toRadians(45),new TranslationalVelConstraint(30))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.BUCKETDROPOFF))))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.DROPOFF))))
                .afterTime(0.3, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.OPEN))))
                .afterTime(0.5, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.intakeOuttake())))
                .afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.intakeStop())))
                .afterTime(1.3, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.CLOSED))))
                .afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP))))
                .afterTime(1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP))))
                .afterTime(1.5, ()-> CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.LOWBUCKET))))
                .waitSeconds(1)
                .splineToSplineHeading(new Pose2d(30, 12,Math.toRadians(270)), Math.toRadians(180))
                .afterTime(0, ()-> CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.clawSubsystem.closeClaw())))
                .afterTime(0, ()-> CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.AUTO))))
                .splineToConstantHeading(new Vector2d(20, 12), Math.toRadians(180), new TranslationalVelConstraint(20))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()-> robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP))))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOPARK))))
                .build();

        robotBase.alliance = ITDCrabEnums.EnmAlliance.BLUE;

        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
            module.clearBulkCache();
        }

    }

    @Override
    public void init_loop() {
        CommandScheduler.getInstance().run();
        telemetry.addData("Wait time", waitSec);
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
        CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP)));
    }

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
     //   robotBase.drive.updatePoseEstimate();
     //   telemetry.addData("x", robotBase.drive.pose.position.x);
     //   telemetry.addData("y", robotBase.drive.pose.position.y);
     //   telemetry.addData("heading (deg)", Math.toDegrees(robotBase.drive.pose.heading.toDouble()));
        //telemetry.addData("Feild Position y")
    //    telemetry.addData("Shoulder Position", robotBase.shoulderSubsystem.shoulderGetPosition());
        blueLeftAction.run(telemetryPacket);
    }

    @Override
    public void stop() {
        robotBase.drive.updatePoseEstimate();
        DataStorage.alliance = robotBase.alliance;
        DataStorage.dblIMUFinalHeadingRad = robotBase.drive.otos.getPosition().h;

    }
}
