package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.EjectCommandGroup;
import org.firstinspires.ftc.teamcode.commands.ExtensionHomeCommandGroup;
import org.firstinspires.ftc.teamcode.commands.PickupElbowWristCommandGroup;
import org.firstinspires.ftc.teamcode.commands.ShoulderHomeCommandGroup;
import org.firstinspires.ftc.teamcode.commands.subPickupToggle;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

@TeleOp(name = ("Crab TeleOp"))
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

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();

        /*int intHeadingFix = -90;
        if (DataStorage.alliance == ITDCrabEnums.EnmAlliance.BLUE) {
            intHeadingFix = 90;
        } else if(DataStorage.alliance == ITDCrabEnums.EnmAlliance.RED){
            intHeadingFix = -0;
        }
        robotBase.drive.otos.setPosition(new SparkFunOTOS.Pose2D(0, 0, DataStorage.dblIMUFinalHeadingRad + Math.toRadians(intHeadingFix)));*/

        robotBase = new RobotBase(hardwareMap);
        chassisController = new GamepadEx(gamepad1);
        armController = new GamepadEx(gamepad2);

        chassisController.getGamepadButton(GamepadKeys.Button.START)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> robotBase.drive.otos.setPosition(new SparkFunOTOS.Pose2D(0, 0, Math.toRadians(0))))
                ));
        chassisController.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> bolFieldCentric = !bolFieldCentric)
                ));
       /* armController.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.HOME))
                ));
        armController.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.HOME))
                ));
        armController.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP))
                ));
        armController.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() -> robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.DROPOFF))
                ));
                */
        armController.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(()-> CommandScheduler.getInstance().schedule(
                        new ExtensionHomeCommandGroup(robotBase.extensionSubsystem)
                ));
        armController.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                        .whenPressed(()->CommandScheduler.getInstance().schedule(
                                new InstantCommand(()-> robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.MAXPOSITION))
                        ));
        chassisController.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(
                        new InstantCommand(() ->robotBase.clawSubsystem.toggleClaw())
                ));
        armController.getGamepadButton(GamepadKeys.Button.BACK)
                .whenPressed(new InstantCommand(() -> CommandScheduler.getInstance().cancelAll()));

        armController.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.TESTPOSITION)));

        armController.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP)));

        armController.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.BUCKETDROPOFF)));

        armController.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.HOME)));

        armController.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.HOME)));

        armController.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP)));

        armController.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new PickupElbowWristCommandGroup(robotBase.wristSubsystem, robotBase.elbowSubsystem));

        chassisController.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed((new EjectCommandGroup(robotBase.intakeSubsystem)));

        armController.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed((new subPickupToggle(robotBase.wristSubsystem, robotBase.elbowSubsystem)));

        armController.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed((new ExtensionHomeCommandGroup(robotBase.extensionSubsystem)));

        armController.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(((new ShoulderHomeCommandGroup(robotBase.shoulderSubsystem))));
    }

    public void loop(){
        telemetry.update();
        chassisController.readButtons();
        armController.readButtons();
        double botHeading = robotBase.drive.otos.getPosition().h;
        ElapsedTime timer = new ElapsedTime();
        int dblCurrentTime = (int) timer.seconds();

        double chassisLeftStickY = chassisController.getLeftY() * Math.abs(chassisController.getLeftY());
        double chassisLeftStickX = chassisController.getLeftX() * Math.abs(chassisController.getLeftX());
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
        robotBase.backRightMotor.setPower(dubBackRightPower);

        robotBase.intakeSubsystem.intakeSpeed(((chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)-chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER))/2)+0.5);

       if(armController.getRightY() > 0.1){
            robotBase.shoulderSubsystem.goUp(armController.getRightY());
        }

        if(armController.getRightY() < -0.05 && armController.getRightY() >= -0.3){
            robotBase.shoulderSubsystem.goDown(armController.getRightY());
        }

        if(armController.getRightY() <= 0.1 && armController.getRightY() >= -0.1){
            robotBase.shoulderSubsystem.stopInPlace();
        }


        if(gamepad1.right_trigger > 0.1 && robotBase.shoulderSubsystem.isShoulderHome()){
            robotBase.extensionSubsystem.intMaxPosition = Extension.ExtensionPosition.MAXSHOULDERDOWNPOSITION.height;
            robotBase.extensionSubsystem.extendForward(gamepad1.right_trigger);
        }

        if(gamepad1.right_trigger > 0.1 && !robotBase.shoulderSubsystem.isShoulderHome()){
            robotBase.extensionSubsystem.intMaxPosition = Extension.ExtensionPosition.MAXSHOULDERUPPOSITION.height;
            robotBase.extensionSubsystem.extendForward(gamepad1.right_trigger);
        }

        if(gamepad1.left_trigger > 0.1){
            robotBase.extensionSubsystem.extendBack(gamepad2.left_trigger);
        }

        if(gamepad1.left_trigger <= 0.1 && gamepad2.right_trigger <= 0.1){
            robotBase.extensionSubsystem.stopInPlace();
        }
        //Connor: I don't think we need these but I commented them just in case.
        /*telemetry.addData("Chassis Left Stick Y", chassisLeftStickY);
        telemetry.addData("Chassis Left Stick X", chassisLeftStickX);
        telemetry.addData("Chassis Right Stick X", chassisRightStickX);
        */

       /* if(robotBase.timerSubsystem.hasEndgamePassed(dblCurrentTime)){
            gamepad1.rumble(0.25, 0.25, 500);
            gamepad2.rumble(0.25, 0.25, 500);
        };
        */
        telemetry.addData("Arm Right Stick Y", armController.getRightY());
        telemetry.addData("Shoulder Position", robotBase.shoulderSubsystem.shoulderGetPosition());
        telemetry.addData("Shoulder Power" , robotBase.shoulderSubsystem.getPower());
        telemetry.addData("Extension Position", robotBase.extensionSubsystem.extensionGetPosition());
        telemetry.addData("Extension Power", robotBase.extensionSubsystem.getPower());
        telemetry.addData("Extension Target Position:", robotBase.extensionSubsystem.getTargetPosition());
        //telemetry.addData("Color Sensor", robotBase.intakeSubsystem.checkSampleColor());
        telemetry.addData("FieldCentric", bolFieldCentric);
        telemetry.addData("Gyro", Math.toDegrees(robotBase.drive.otos.getPosition().h));
        telemetry.addData("Arm Right Trigger", gamepad2.right_trigger);
        telemetry.addData("Arm Left Trigger", gamepad2.left_trigger);
        telemetry.addData("Shoulder Limit Switch", robotBase.shoulderSubsystem.isShoulderHome());
        telemetry.addData("Extension Limit Switch", robotBase.extensionSubsystem.isExtensionHome());

        CommandScheduler.getInstance().run();

    }
}
