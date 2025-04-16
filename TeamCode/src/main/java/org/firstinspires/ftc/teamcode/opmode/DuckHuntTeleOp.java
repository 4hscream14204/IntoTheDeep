package org.firstinspires.ftc.teamcode.opmode;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.HighFreightCommandGroup;
import org.firstinspires.ftc.teamcode.commands.LowFreightCommandGroup;
import org.firstinspires.ftc.teamcode.commands.MediumFreightCommandGroup;

@TeleOp(name = ("Duck Hunt TeleOp"))
public class DuckHuntTeleOp extends OpMode {
    public RobotBase robotBase;
    public GamepadEx chassisController;
    public GamepadEx armController;

    @Override
    public void init(){
        CommandScheduler.getInstance().reset();
        robotBase = new RobotBase(hardwareMap);
        chassisController = new GamepadEx(gamepad1);
        armController = new GamepadEx(gamepad2);
        robotBase.chassisSubsystem.bolFieldCentric = false;

        /*chassisController.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.liftSubsystem.bucketServo.setPosition(0.15))));

        chassisController.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.liftSubsystem.bucketServo.setPosition(0.85))));*/

        chassisController.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(()-> CommandScheduler.getInstance().schedule(new HighFreightCommandGroup(robotBase.liftSubsystem)));

        chassisController.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(()-> CommandScheduler.getInstance().schedule(new MediumFreightCommandGroup(robotBase.liftSubsystem)));

        chassisController.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(()-> CommandScheduler.getInstance().schedule(new LowFreightCommandGroup(robotBase.liftSubsystem)));

        new Trigger(()->chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1)
                .or(new Trigger(()->chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1))
                .whileActiveContinuous(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.intakeMotor.setPower( chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) - chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)))
                ))
                .whenInactive(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.intakeMotor.setPower(0))
                ));

        chassisController.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenActive(()-> CommandScheduler.getInstance().schedule(
                        new InstantCommand(()-> robotBase.carouselServo.setPosition(1))
                ));

        chassisController.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenInactive(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.carouselServo.setPosition(0.5))
                ));

        chassisController.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenActive(()-> CommandScheduler.getInstance().schedule(
                        new InstantCommand(()-> robotBase.carouselServo.setPosition(0))
                ));

        chassisController.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenInactive(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.carouselServo.setPosition(0.5))
                ));
    }

    public void loop(){
        telemetry.update();
        chassisController.readButtons();
        armController.readButtons();

        robotBase.chassisSubsystem.drive(chassisController.getLeftX(), chassisController.getLeftY(), chassisController.getRightX());

        telemetry.addData("Lift Encoder", robotBase.liftSubsystem.liftMotor.getCurrentPosition());

        CommandScheduler.getInstance().run();
    }
}
