package org.firstinspires.ftc.teamcode.opmode.auto;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ScheduleCommand;
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
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Sweeper;
import org.firstinspires.ftc.teamcode.subsystems.TimerLED;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
@Disabled
@Autonomous(name = "CRIRedCenterRightx4")
public class CRIRedRightSpecimen extends OpMode{
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
        startPose = new Pose2d(0, 0, Math.toRadians(0));
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
        CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.BUCKETDROPOFF)));

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
                .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBER)))))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOAVOIDENCE)))))
                .afterTime(0.1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP))))
                .afterTime(0.1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.BUCKETDROPOFF))))
                .setTangent(Math.toRadians(270))
                //Chamber avoidence
                .splineToConstantHeading(new Vector2d(27, 16.2), Math.toRadians(0), new TranslationalVelConstraint(40))
                //Chamber drop off
                .splineToConstantHeading(new Vector2d(53, 16.2), Math.toRadians(0), new TranslationalVelConstraint(30))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOPARK)))))
                .splineToConstantHeading(new Vector2d(56, 10), Math.toRadians(270), new TranslationalVelConstraint(30))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBERCLAMP)))))
                .afterTime(0.2, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.clawSubsystem.openClaw()))))
                .waitSeconds(0.4)
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(50, 18), Math.toRadians(180), new TranslationalVelConstraint(30))
                .setTangent(45)
                .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME)))))
                .afterTime(.2, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.sweeperSubsystem.goToPosition(Sweeper.SweeperPosition.MIDDLE)))))
                //First sample sweep pos
                .splineToConstantHeading(new Vector2d(44, 18), Math.toRadians(180), new TranslationalVelConstraint(30))
                .waitSeconds(0.1)
                .setTangent(Math.toRadians(0))
                //First sample drop off
                .splineToConstantHeading(new Vector2d(6, 16.8), Math.toRadians(180), new TranslationalVelConstraint(60))
                .afterTime(0.0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.sweeperSubsystem.goToPosition(Sweeper.SweeperPosition.OUT)))))
                //Second sample return
                .setTangent(Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(52, 17.2), Math.toRadians(0), new TranslationalVelConstraint(30))
                .afterTime(0.0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.sweeperSubsystem.goToPosition(Sweeper.SweeperPosition.MIDDLE)))))
                .waitSeconds(0.1)
                .setTangent(Math.toRadians(0))
                //Second sample drop off
                .splineToConstantHeading(new Vector2d(6, 16.8), Math.toRadians(180), new TranslationalVelConstraint(60))
                .afterTime(0.0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.sweeperSubsystem.goToPosition(Sweeper.SweeperPosition.OUT)))))
                /* //Third sample return
                 .splineToConstantHeading(new Vector2d(-64, 17.4), Math.toRadians(180), new TranslationalVelConstraint(30))
                 .afterTime(0.0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.sweeperSubsystem.goToPosition(Sweeper.SweeperPosition.MIDDLE)))))
                 .waitSeconds(0.4)
                 .setTangent(0)
                 //Third sample drop off
                 .splineToConstantHeading(new Vector2d(-7, 17), Math.toRadians(0), new TranslationalVelConstraint(40))
                 .afterTime(0.0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.sweeperSubsystem.goToPosition(Sweeper.SweeperPosition.MIDDLE)))))
                 */
                .splineToLinearHeading(new Pose2d(14.0, 16.00, Math.toRadians(270.00)), Math.toRadians(180.00), new TranslationalVelConstraint(50))
                .afterTime(0.0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.sweeperSubsystem.goToPosition(Sweeper.SweeperPosition.OUT)))))
                .waitSeconds(0.2)
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(-3, 16.8), Math.toRadians(0), new TranslationalVelConstraint(50))
                .afterTime(0.2, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.clawSubsystem.closeClaw()))))
                .afterTime(0.4, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBER)))))
                .waitSeconds(0.2)
                .setTangent(Math.toRadians(180))
                .splineToSplineHeading(new Pose2d(20.0, 17.0, Math.toRadians(180.00)), Math.toRadians(0.00), new TranslationalVelConstraint(30))

                .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOAVOIDENCE)))))
                .splineToConstantHeading(new Vector2d(52, 18), Math.toRadians(0), new TranslationalVelConstraint(30))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOPARK)))))
                .splineToConstantHeading(new Vector2d(56, 10), Math.toRadians(90), new TranslationalVelConstraint(30))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBERCLAMP)))))
                .afterTime(0.4, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.clawSubsystem.openClaw()))))
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(53, 18), Math.toRadians(180), new TranslationalVelConstraint(30))
                //.setTangent(45)
                .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME)))))
                .splineToSplineHeading(new Pose2d(13.0, 16.8, Math.toRadians(270.00)), Math.toRadians(180.00), new TranslationalVelConstraint(30))
                .splineToConstantHeading(new Vector2d(-4, 16.8), Math.toRadians(180), new TranslationalVelConstraint(50))
                .afterTime(0.2, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.clawSubsystem.closeClaw()))))
                .afterTime(0.4, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBER)))))
                .waitSeconds(0.2)
                .setTangent(Math.toRadians(180))
                .splineToSplineHeading(new Pose2d(20.0, 17.0, Math.toRadians(180.00)), Math.toRadians(0.00), new TranslationalVelConstraint(30))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOAVOIDENCE)))))
                .splineToConstantHeading(new Vector2d(53, 18), Math.toRadians(0), new TranslationalVelConstraint(30))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOPARK)))))
                .splineToConstantHeading(new Vector2d(58, 10), Math.toRadians(90), new TranslationalVelConstraint(30))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBERCLAMP)))))
                .afterTime(0.4, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.clawSubsystem.openClaw()))))
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(53, 19), Math.toRadians(180), new TranslationalVelConstraint(40))
                .setTangent(Math.toRadians(0))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME)))))
                .splineToSplineHeading(new Pose2d(13.0, 16.8, Math.toRadians(270.00)), Math.toRadians(180.00), new TranslationalVelConstraint(40))
                .splineToConstantHeading(new Vector2d(-5.0, 16.8), Math.toRadians(180), new TranslationalVelConstraint(50))
                .afterTime(0.2, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.clawSubsystem.closeClaw()))))
                .afterTime(0.4, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBER)))))
                .waitSeconds(0.2)
                .setTangent(Math.toRadians(180))
                .splineToSplineHeading(new Pose2d(20.0, 19, Math.toRadians(180.00)), Math.toRadians(0.00), new TranslationalVelConstraint(40))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOAVOIDENCE)))))
                .afterTime(0.5, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOPARK)))))
                .splineToConstantHeading(new Vector2d(50, 18), Math.toRadians(0), new TranslationalVelConstraint(30))
                .afterTime(0.1, ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.BUCKETDROPOFF))))
                .splineToConstantHeading(new Vector2d(55, 10), Math.toRadians(0), new TranslationalVelConstraint(30))
                .afterTime(0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBERCLAMP)))))
                .afterTime(0.0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PRESUBPICKUP)))))
                .afterTime(0.0, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PRESUBPICKUP)))))
                .afterTime(0.4, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.clawSubsystem.openClaw()))))
                .afterTime(0.1, ()->CommandScheduler.getInstance().schedule((new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.HOME)))))
                .waitSeconds(1)
                .build();

        robotBase.alliance = ITDCrabEnums.EnmAlliance.RED;
        robotBase.ledSubsystem.setColor(TimerLED.Colors.RED);

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

    }

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
        robotBase.drive.updatePoseEstimate();
        telemetry.addData("x", robotBase.drive.pose.position.x);
        telemetry.addData("y", robotBase.drive.pose.position.y);
        telemetry.addData("heading (deg)", Math.toDegrees(robotBase.drive.pose.heading.toDouble()));
        //telemetry.addData("Feild Position y")
        //    telemetry.addData("Shoulder Position", robotBase.shoulderSubsystem.shoulderGetPosition());
        blueLeftAction.run(telemetryPacket);
    }

    @Override
    public void stop() {
        robotBase.drive.updatePoseEstimate();
        DataStorage.alliance = robotBase.alliance;
        DataStorage.dblIMUFinalHeadingRad = robotBase.drive.otos.getPosition().h + 90;

    }
}
