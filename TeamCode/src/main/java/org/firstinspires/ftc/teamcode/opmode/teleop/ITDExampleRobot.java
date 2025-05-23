package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

@TeleOp
public class ITDExampleRobot extends OpMode {
    public RobotBase robotBase;
    public GamepadEx armController;
    public GamepadEx chassisController;

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        robotBase = new RobotBase(hardwareMap);

        chassisController = new GamepadEx(gamepad1);
        armController = new GamepadEx(gamepad2);

        armController.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenActive(()-> CommandScheduler.getInstance().schedule(
                        new InstantCommand(()-> robotBase.intakeSubsystem.intakeSpeed(0.8))
                ));

        armController.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenInactive(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.intakeSubsystem.intakeSpeed(0.5))
                ));

        armController.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenActive(()-> CommandScheduler.getInstance().schedule(
                        new InstantCommand(()-> robotBase.intakeSubsystem.intakeSpeed(0))
                ));

        armController.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenInactive(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.intakeSubsystem.intakeSpeed(0.5))
                ));

        new Trigger(()->armController.getRightY() > 0.01)
                .or(new Trigger(()->armController.getRightY() < -0.01))
                .whileActiveContinuous(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.armSubsystem.move(armController.getRightY())))
                )
                .whenInactive(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.armSubsystem.stopInPlace())
                ));

        armController.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(()-> CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.FOLDEDIN))
                ));

        armController.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(()-> CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.FOLDEDOUT))
                ));
    }

    public void loop() {
        chassisController.readButtons();
        armController.readButtons();

        robotBase.chassisSubsystem.drive(chassisController.getLeftX(), chassisController.getLeftY(), chassisController.getRightX());

        telemetry.addData("AMP", robotBase.armSubsystem.getAMP());
        telemetry.addData("is stalling", robotBase.armSubsystem.isStalling());
        telemetry.addData("arm stopped", robotBase.armSubsystem.bolStoppedInPlace);
        telemetry.addData("current position", robotBase.armSubsystem.intCurrentPos);
        telemetry.addData("intake speed", robotBase.intakeSubsystem.getSpeed());
        telemetry.addData("Max AMPs", robotBase.armSubsystem.maxAmps);
        telemetry.addData("Invalid direction", robotBase.armSubsystem.dblInvalidDirection);
        telemetry.addData("Current direction", robotBase.armSubsystem.dblCurrentDirection);
        telemetry.addData("Arm power", robotBase.armSubsystem.getPower());

        CommandScheduler.getInstance().run();
    }
}
