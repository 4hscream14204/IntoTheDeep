package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ScheduleCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.ITDCrabEnums;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.BucketElbowWristCommandGroup;
import org.firstinspires.ftc.teamcode.commands.BucketExtendUpCommandGroup;
import org.firstinspires.ftc.teamcode.commands.ChamberCommandGroup;
import org.firstinspires.ftc.teamcode.commands.EjectCommandGroup;
import org.firstinspires.ftc.teamcode.commands.ElbowWristHomeCommandGroup;
import org.firstinspires.ftc.teamcode.commands.ExtensionControlCommandGroup;
import org.firstinspires.ftc.teamcode.commands.ExtensionHomeCommandGroup;
import org.firstinspires.ftc.teamcode.commands.GyroResetCommandGroup;
import org.firstinspires.ftc.teamcode.commands.RejectCommandGroup;
import org.firstinspires.ftc.teamcode.commands.SecondLevelAscentCommandGroup;
import org.firstinspires.ftc.teamcode.commands.SetHeadingDegreesCommandGroup;
import org.firstinspires.ftc.teamcode.commands.ShoulderHomeCommandGroup;
import org.firstinspires.ftc.teamcode.commands.ShoulderToggleCommandGroup;
import org.firstinspires.ftc.teamcode.commands.SpecimenWallPickUpCommandGroup;
import org.firstinspires.ftc.teamcode.commands.SubPickupReturnCommandGroup;
import org.firstinspires.ftc.teamcode.commands.SubPickupToggleCommandGroup;
import org.firstinspires.ftc.teamcode.commands.TeleOpStartCommandGroup;
import org.firstinspires.ftc.teamcode.commands.ToggleAllianceCommandGroup;
import org.firstinspires.ftc.teamcode.commands.ToggleStrategyCommandGroup;
import org.firstinspires.ftc.teamcode.commands.ToggleSweeperCommandGroup;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Sweeper;


@TeleOp(name = ("Aristocrab TeleOp"))
public class CrabTeleOp extends OpMode {
    public RobotBase robotBase;
    boolean bolFieldCentric = true;
    double dubFrontRightPower;
    double dubFrontLeftPower;
    double dubBackRightPower;
    double dubBackLeftPower;
    double dubDenominator;
    public GamepadEx armController;
    public GamepadEx chassisController;
    public boolean bolIsInitLoop = true;
    double dblCurrentTime;


    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        robotBase = new RobotBase(hardwareMap);
        int intHeadingFix = 180;
        /*if (DataStorage.alliance == ITDCrabEnums.EnmAlliance.BLUE) {
            intHeadingFix = 90;
        } else if(DataStorage.alliance == ITDCrabEnums.EnmAlliance.RED){
            intHeadingFix = -90;
        }*/
        robotBase.drive.otos.setPosition(new SparkFunOTOS.Pose2D(0, 0, DataStorage.dblIMUFinalHeadingRad + Math.toRadians(intHeadingFix)));

        chassisController = new GamepadEx(gamepad1);
        armController = new GamepadEx(gamepad2);
        robotBase.extensionSubsystem.intMaxPosition = Extension.ExtensionPosition.MAXSHOULDERDOWNPOSITION.height;



        chassisController.getGamepadButton(GamepadKeys.Button.START)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(
                        new GyroResetCommandGroup(robotBase)
                        ));
        chassisController.getGamepadButton(GamepadKeys.Button.BACK)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> bolFieldCentric = !bolFieldCentric)
                ));
        chassisController.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() ->robotBase.clawSubsystem.toggleClaw())
                ));
        /*chassisController.getGamepadButton(GamepadKeys.Button.A)
                        .whenPressed(()->CommandScheduler.getInstance().schedule(
                                new SubPickupToggleCommandGroup(robotBase.wristSubsystem, robotBase.elbowSubsystem, robotBase.intakeSubsystem, robotBase.shoulderSubsystem)
                        ));*/

        chassisController.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                        .whenPressed(()->CommandScheduler.getInstance().schedule(
                                new SetHeadingDegreesCommandGroup(robotBase, 315)
                        ));

        chassisController.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(()->CommandScheduler.getInstance().schedule( new EjectCommandGroup(robotBase)));

        chassisController.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                        .whenActive(()-> CommandScheduler.getInstance().schedule(
                                new InstantCommand(()-> robotBase.intakeSubsystem.intakeSpeed(0.8))
                        ));

        chassisController.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                        .whenInactive(()->CommandScheduler.getInstance().schedule(
                                new InstantCommand(()->robotBase.intakeSubsystem.intakeStop())
                        ));

        chassisController.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenActive(()-> CommandScheduler.getInstance().schedule(
                        new InstantCommand(()-> robotBase.intakeSubsystem.intakeSpeed(0))
                ));

        chassisController.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenInactive(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.intakeSubsystem.intakeStop())
                ));
        chassisController.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                        .whenPressed(
                                ()->CommandScheduler.getInstance().schedule(new SpecimenWallPickUpCommandGroup(robotBase, robotBase.shoulderSubsystem, robotBase.clawSubsystem, robotBase.extensionSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem))
                        );

        chassisController.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                        .whenPressed(
                                ()->CommandScheduler.getInstance().schedule(new ElbowWristHomeCommandGroup(robotBase))
                        );

        /*chassisController.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                        .whenPressed(()->CommandScheduler.getInstance().schedule(
                                new ToggleSweeperCommandGroup(robotBase)
                        ));*/



        chassisController.getGamepadButton(GamepadKeys.Button.BACK)
                        .whenPressed(
                                ()->CommandScheduler.getInstance().schedule(new ToggleStrategyCommandGroup()
                                ));

        chassisController.getGamepadButton(GamepadKeys.Button.A)
                        .whenPressed(
                                ()->CommandScheduler.getInstance().schedule(new SetHeadingDegreesCommandGroup(robotBase, 0))
                        );

       /* chassisController.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                        .toggleWhenPressed(
                                new PickupSpecimenOffWallLineUpCommandGroup(robotBase.extensionSubsystem,robotBase.shoulderSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem, robotBase.clawSubsystem),
                                new PickupSpecimenOffWallGrabCommandGroup(robotBase.extensionSubsystem, robotBase.shoulderSubsystem, robotBase.clawSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem)
                        );

        */

       /* chassisController.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                        .whenPressed(()->CommandScheduler.getInstance().schedule(
                                new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.BUCKETDROPOFF))
                        ));*/

        chassisController.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                        .whenPressed(new BucketElbowWristCommandGroup(robotBase));

        chassisController.getGamepadButton(GamepadKeys.Button.Y)
                        .and(new GamepadButton(chassisController, GamepadKeys.Button.RIGHT_BUMPER))
                        .whenActive(()->CommandScheduler.getInstance().schedule( new SecondLevelAscentCommandGroup(robotBase)));

        armController.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                        .whenPressed(()->CommandScheduler.getInstance().schedule(
                                new SubPickupReturnCommandGroup(robotBase)
                        ));
        armController.getGamepadButton(GamepadKeys.Button.BACK)
                .whenPressed(new InstantCommand(() -> CommandScheduler.getInstance().cancelAll()));

        armController.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new SubPickupToggleCommandGroup(robotBase.wristSubsystem, robotBase.elbowSubsystem, robotBase.intakeSubsystem, robotBase.shoulderSubsystem)/*new SubPickupTogglePreSubPickupCommandGroup(robotBase.wristSubsystem, robotBase.elbowSubsystem), new SubPickupTogglePickupCommandGroup(robotBase)*/));

        armController.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed((new ExtensionHomeCommandGroup(robotBase.extensionSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem)));

        armController.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(((new ShoulderHomeCommandGroup(robotBase.shoulderSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem))));

        armController.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(()->CommandScheduler.getInstance().schedule(
                        new ShoulderToggleCommandGroup(robotBase)
                ));

        //high basket button combo
        armController.getGamepadButton(GamepadKeys.Button.Y)
                .and(new GamepadButton(armController, GamepadKeys.Button.LEFT_BUMPER))
                .whenActive(()->CommandScheduler.getInstance().schedule( new BucketExtendUpCommandGroup(robotBase, Shoulder.ShoulderPosition.HIGHBASKET, Extension.ExtensionPosition.HIGHBUCKET)));

                //low basket button combo
        armController.getGamepadButton(GamepadKeys.Button.B)
                .and(new GamepadButton(armController, GamepadKeys.Button.LEFT_BUMPER))
                .whenActive(()->CommandScheduler.getInstance().schedule( new BucketExtendUpCommandGroup(robotBase, Shoulder.ShoulderPosition.LOWBASKET, Extension.ExtensionPosition.LOWBUCKET)));

        //high Chamber button combo
        armController.getGamepadButton(GamepadKeys.Button.Y)
                .and(new GamepadButton(armController, GamepadKeys.Button.RIGHT_BUMPER))
                .whenActive(()->CommandScheduler.getInstance().schedule(new ChamberCommandGroup(robotBase, Shoulder.ShoulderPosition.NEWHIGHCHAMBER, Extension.ExtensionPosition.HIGHCHAMBER, Extension.ExtensionPosition.HIGHCHAMBERCLAMP)));

        //high Low button combo
        armController.getGamepadButton(GamepadKeys.Button.B)
                .and(new GamepadButton(armController, GamepadKeys.Button.RIGHT_BUMPER))
                .whenActive(()->CommandScheduler.getInstance().schedule( new ChamberCommandGroup(robotBase, Shoulder.ShoulderPosition.NEWLOWCHAMBER, Extension.ExtensionPosition.LOWCHAMBER, Extension.ExtensionPosition.HOME)));

        armController.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(
                        ()->CommandScheduler.getInstance().schedule(new SpecimenWallPickUpCommandGroup(robotBase, robotBase.shoulderSubsystem, robotBase.clawSubsystem, robotBase.extensionSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem))
                );

        armController.getGamepadButton(GamepadKeys.Button.START)
                .whenPressed(()->CommandScheduler.getInstance().schedule(
                        new ToggleAllianceCommandGroup()
                ));

        new Trigger(()->chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1)
                .or(new Trigger(()->chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1))
                        .whileActiveContinuous(()->CommandScheduler.getInstance().schedule(
                                new ExtensionControlCommandGroup(robotBase, chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) - chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER))
                        ))
                        .whenInactive(()->CommandScheduler.getInstance().schedule(
                                new InstantCommand(()->robotBase.extensionSubsystem.stopInPlace())
                        ));

        new Trigger(()->armController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1)
                .whileActiveContinuous(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.sweeperSubsystem.setPosition(chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)))
                ))
                .whenInactive(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.sweeperSubsystem.setPosition(Sweeper.SweeperPosition.HOME.position))
                ));

        new Trigger(()->armController.getRightY() > 0.01)
                .or(new Trigger(()->armController.getRightY() < -0.01))
                .whileActiveContinuous(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.shoulderSubsystem.goUpOrDown(armController.getRightY())))
                )
                .whenInactive(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.shoulderSubsystem.stopInPlace())
                ));

        /*new Trigger(()->chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1)
                .whileActiveContinuous(()->CommandScheduler.getInstance().schedule(
                   new InstantCommand(()->robotBase.extensionSubsystem.extend(-chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)))
                ))
                .whenInactive(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.extensionSubsystem.stopInPlace())
                ));

        new Trigger(()->chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1)
                .whileActiveContinuous(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.extensionSubsystem.extend(chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)))
                ))
                .whenInactive(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.extensionSubsystem.stopInPlace())
                ));*/

        new Trigger(()->robotBase.extensionSubsystem.isExtensionHome())
                .whenActive(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.extensionSubsystem.reset())
                ));

        new Trigger(()->robotBase.shoulderSubsystem.isShoulderHome())
                .whenActive(
                        ()-> CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.shoulderSubsystem.reset())
                ));

        new Trigger(()->robotBase.intakeSubsystem.isWrongColor())
                .whenActive(
                        ()->CommandScheduler.getInstance().schedule(new RejectCommandGroup(robotBase)
                ));

        new Trigger(()->robotBase.intakeSubsystem.isRightColor())
                .whenActive(()->CommandScheduler.getInstance().schedule(new SubPickupReturnCommandGroup(robotBase)));

       /* new Trigger(()->robotBase.intakeSubsystem.isBlueSample() && DataStorage.alliance == ITDCrabEnums.EnmAlliance.RED)
                .whenActive(
                        new SampleOuttakeCommandGroup(robotBase)
                );

        new Trigger(()->robotBase.intakeSubsystem.isRedSample() && DataStorage.alliance == ITDCrabEnums.EnmAlliance.BLUE)
                .whenActive(
                        new SampleOuttakeCommandGroup(robotBase)
                );*/

        /*new Trigger(()->!robotBase.shoulderSubsystem.isShoulderHome() && robotBase.extensionSubsystem.isPastMaxPosition())
                .whenActive(()->CommandScheduler.getInstance().schedule(
                        new ExtensionMaximumPositionCommandGroup(robotBase)
                ));*/

        /*armController.getGamepadButton(GamepadKeys.Button.X)
                .and(new GamepadButton( armController, GamepadKeys.Button.RIGHT_BUMPER))
                .toggleWhenActive(new NewChamberLineUpCommandGroup(robotBase, Shoulder.ShoulderPosition.NEWHIGHCHAMBER, Extension.ExtensionPosition.NEWHIGHCHAMBER),
                        new NewChamberReleaseCommandGroup(robotBase, Extension.ExtensionPosition.NEWHIGHCHAMBERCLAMP));*/

    }

    /*public void init_loop(){
        CommandScheduler.getInstance().run();
    }*/
    public void start(){
        CommandScheduler.getInstance().schedule(new TeleOpStartCommandGroup(robotBase));
        robotBase.chassisSubsystem.timer.reset();
        robotBase.chassisSubsystem.setTargetDegrees(Math.toDegrees(robotBase.drive.otos.getPosition().h));
        robotBase.chassisSubsystem.disablePIDUse();
    }

    public void loop(){
        chassisController.readButtons();
        armController.readButtons();
        double loopTimer = robotBase.chassisSubsystem.timer.milliseconds() - dblCurrentTime;
        dblCurrentTime = robotBase.chassisSubsystem.timer.milliseconds();
        robotBase.intakeSubsystem.getTime(dblCurrentTime);

        robotBase.chassisSubsystem.drive(chassisController.getLeftX(), chassisController.getLeftY(), chassisController.getRightX());

        /*double chassisLeftStickX = (chassisController.getLeftY() * Math.abs(chassisController.getLeftY()) * -1);
        double chassisLeftStickY = chassisController.getLeftX() * Math.abs(chassisController.getLeftX());
        double chassisRightStickX = chassisController.getRightX() * Math.abs(chassisController.getRightX());
        double rotX = chassisLeftStickX * Math.cos(-botHeading) - chassisLeftStickY * Math.sin(-botHeading);
        double rotY = chassisLeftStickX * Math.sin(-botHeading) + chassisLeftStickY * Math.cos(-botHeading);

        if (bolFieldCentric) {
            dubDenominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(chassisRightStickX), 1);
            dubFrontLeftPower = (rotY + rotX + chassisRightStickX) / dubDenominator;
            dubBackLeftPower = (rotY - rotX + chassisRightStickX) / dubDenominator;
            dubFrontRightPower = (rotY - rotX - chassisRightStickX) / dubDenominator;
            dubBackRightPower = (rotY + rotX - chassisRightStickX) / dubDenominator;
        } else {
            dubDenominator = Math.max(Math.abs(chassisLeftStickX) + Math.abs(chassisLeftStickX) + Math.abs(chassisRightStickX), 1);
            dubFrontLeftPower = (chassisLeftStickY + chassisLeftStickX + chassisRightStickX) / dubDenominator;
            dubBackLeftPower = (chassisLeftStickY - chassisLeftStickX + chassisRightStickX) / dubDenominator;
            dubFrontRightPower = (chassisLeftStickY - chassisLeftStickX - chassisRightStickX) / dubDenominator;
            dubBackRightPower = (chassisLeftStickY + chassisLeftStickX - chassisRightStickX) / dubDenominator;
        }
        robotBase.frontLeftMotor.setPower(dubFrontLeftPower);
        robotBase.backLeftMotor.setPower(dubBackLeftPower);
        robotBase.frontRightMotor.setPower(dubFrontRightPower);
        robotBase.backRightMotor.setPower(dubBackRightPower);*/

        robotBase.ledSubsystem.ledSuggestion(dblCurrentTime);
        robotBase.intakeSubsystem.displaySampleColor();

        /*if(!robotBase.shoulderSubsystem.isShoulderHome()){
            robotBase.extensionSubsystem.intMaxPosition = Extension.ExtensionPosition.MAXSHOULDERUPPOSITION.height;
        }
        else{
            robotBase.extensionSubsystem.intMaxPosition = Extension.ExtensionPosition.MAXSHOULDERDOWNPOSITION.height;
        }*/

     //   robotBase.intakeSubsystem.intakeSpeed(((chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)-chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER))/2)+0.5);

       /*if(armController.getRightY() > 0.1){
            robotBase.shoulderSubsystem.goUp(armController.getRightY());
        }

        if(armController.getRightY() < -0.05 && armController.getRightY() >= -0.3){
            robotBase.shoulderSubsystem.goDown(armController.getRightY());
        }

        if(armController.getRightY() <= 0.1 && armController.getRightY() >= -0.1){
            robotBase.shoulderSubsystem.stopInPlace();
        }*/

        //Connor: I don't think we need these but I commented them just in case.
       /* telemetry.addData("Chassis Left Stick Y", chassisLeftStickY);
        telemetry.addData("Chassis Left Stick X", chassisLeftStickX);
        telemetry.addData("Chassis Right Stick X", chassisRightStickX);*/

        //telemetry.addData("Elbow", robotBase.elbowSubsystem.getPosition());
       // telemetry.addData("Wrist", robotBase.wristSubsystem.getPosition());
        /*telemetry.addData("Wrist Enum: ", robotBase.wristSubsystem.enmWristPosition);
        telemetry.addData("Elbow Enum: ", robotBase.elbowSubsystem.enmElbowPosition);
        telemetry.addData("Elbow isAtPosition", robotBase.elbowSubsystem.isAtPosition(Elbow.ElbowPosition.PRESUBPICKUP));
        telemetry.addData("Wrist isAtPosition", robotBase.wristSubsystem.isAtPosition(Wrist.WristPosition.PRESUBPICKUP));*/
       // telemetry.addData("Arm Right Stick Y", armController.getRightY());
        telemetry.addData("Shoulder Position", robotBase.shoulderSubsystem.shoulderGetPosition());
        /*telemetry.addData("Shoulder Power" , robotBase.shoulderSubsystem.getPower());
        telemetry.addData("Shoulder Limit Switch", robotBase.shoulderSubsystem.isShoulderHome());*/
        telemetry.addData("Extension Position", robotBase.extensionSubsystem.extensionGetPosition());
        /*telemetry.addData("Extension Power", robotBase.extensionSubsystem.getPower());
        telemetry.addData("Extension Limit Switch", robotBase.extensionSubsystem.isExtensionHome());
        telemetry.addData("FieldCentric", robotBase.chassisSubsystem.bolFieldCentric);
        telemetry.addData("Gyro", Math.toDegrees(robotBase.drive.otos.getPosition().h));*/
        telemetry.addData("Strategy: ", DataStorage.strategy);
        telemetry.addData("loop time", loopTimer);
        telemetry.addData("Alliance", DataStorage.alliance);
        telemetry.addData("Gyro", robotBase.chassisSubsystem.botPose.h);
        //telemetry.addData("PID", robotBase.chassisSubsystem.isInPIDControl);
       // telemetry.addData("IsInPIDControl", robotBase.chassisSubsystem.isInPIDControl);
        //telemetry.addData("Current Target Heading", Math.toDegrees(robotBase.chassisSubsystem.dblTargetHeading));
        //telemetry.addData("Right stick X", chassisController.getRightX());
        /*telemetry.addData("Chassis Left Trigger", chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));
        telemetry.addData("Chassis Right Trigger", chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));*/
       // telemetry.addData("Maximum Extension", robotBase.extensionSubsystem.intMaxPosition);
       // telemetry.addData("IsPastMaxPosition?", robotBase.extensionSubsystem.isPastMaxPosition());
        /*telemetry.addData("Target Left Extension", robotBase.extensionSubsystem.extendLeftMotor.getTargetPosition());
        telemetry.addData("Target Right Extension", robotBase.extensionSubsystem.extendRightMotor.getTargetPosition());*/
        //telemetry.addData("Hue", robotBase.intakeSubsystem.getHueValues());
        //telemetry.addData("specimens to deliver", robotBase.ledSubsystem.intSpecimensToDeliver);
        telemetry.addData("Timer: ", dblCurrentTime / 1000);
        /*telemetry.addData("Current time", dblCurrentTime);
        /*telemetry.addData("margin of error", robotBase.ledSubsystem.dblMarginOfError);
        telemetry.addData("Hang time", robotBase.ledSubsystem.dblEstimatedHangTime);
        telemetry.addData("cycle time", robotBase.ledSubsystem.dblEstimatedCycleTime);
        //telemetry.addData("Ok to home",robotBase.extensionSubsystem.extensionGetPosition() > Extension.ExtensionPosition.NEWHIGHCHAMBERCLAMP.height);
        /*telemetry.addLine()
                .addData("Red: ", robotBase.intakeSubsystem.checkSampleColorRed())
                .addData("Blue: ", robotBase.intakeSubsystem.checkSampleColorBlue())
                .addData("Green: ", robotBase.intakeSubsystem.checkSampleColorGreen());

        telemetry.addData("IsRed", robotBase.intakeSubsystem.isRedSample());
        telemetry.addData("IsBlue", robotBase.intakeSubsystem.isBlueSample());*/

        CommandScheduler.getInstance().run();
    }
}
