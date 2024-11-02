package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.HighChamberScoreCommandGroup;
import org.firstinspires.ftc.teamcode.commands.IntakeCommandGroup;
import org.firstinspires.ftc.teamcode.commands.IntakeReturnPositionCommandGroup;
import org.firstinspires.ftc.teamcode.commands.IntakeTransferCommandGroup;
import org.firstinspires.ftc.teamcode.commands.LiftGoToBasketCommandGroup;
import org.firstinspires.ftc.teamcode.commands.LiftHighBasketCommandGroup;
import org.firstinspires.ftc.teamcode.commands.LiftHighBasketCommandGroup;
import org.firstinspires.ftc.teamcode.commands.LiftHome;
import org.firstinspires.ftc.teamcode.commands.LiftLowBasketCommandGroup;
import org.firstinspires.ftc.teamcode.commands.OuttakeIntoBucketCommandGroup;
import org.firstinspires.ftc.teamcode.commands.PickupSpecimenGrabSpecimenCommandGroup;
import org.firstinspires.ftc.teamcode.commands.PickupSpecimenLineUpCommandGroup;
import org.firstinspires.ftc.teamcode.commands.PickupSpecimenLineUpCommandGroup;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.SpecimenGrabber;

@TeleOp(name="George")
public class TeleGeorge extends OpMode{
    RobotBase robotBase;
    boolean bolFieldCentric = true;
    double dubDenominator;
    double dubFrontLeftPower;
    double dubBackLeftPower;
    double dubFrontRightPower;
    double dubBackRightPower;
    public GamepadEx armController;
    public GamepadEx baseController;
    private double dblCurrentHeading;
    private double dblTargetHeading = 0;
    private double dblHeadingDeviation = 0;
    private double dblHeadingOutput = 0;
    private double dblCurrentTime = 0;
    private double dblDelayTime;
    private double dblLastStickTime = 0;

    private double dblBlueBasketHeading = Math.toRadians(-45);
    private double dblBlueChamberHeading = Math.toRadians(0);
    private double dblBlueSpecimenPickupHeading = Math.toRadians(180);

    private ElapsedTime timer;

    @Override
    public void init() {
        robotBase =new RobotBase(hardwareMap);
        armController = new GamepadEx(gamepad2);
        baseController = new GamepadEx(gamepad1);
        CommandScheduler.getInstance().reset();

        armController.getGamepadButton(GamepadKeys.Button.Y)
                .and(new GamepadButton(armController, GamepadKeys.Button.LEFT_BUMPER))
                .whenActive(new IntakeCommandGroup(robotBase.zlideSubsystem,robotBase.wristSubsystem));

        armController.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.specimenGrabberSubsystem.toggleGrabber())
                ));

        armController.getGamepadButton(GamepadKeys.Button.A)
                .and(new GamepadButton(armController, GamepadKeys.Button.RIGHT_BUMPER))
                .whenActive(new LiftHome(robotBase.liftSubsystem));

        baseController.getGamepadButton((GamepadKeys.Button.Y))
                .whenPressed(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()-> bolFieldCentric = !bolFieldCentric)
                ));

        armController.getGamepadButton(GamepadKeys.Button.Y)
                .and(new GamepadButton(armController, GamepadKeys.Button.RIGHT_BUMPER))
                .whenActive(new LiftGoToBasketCommandGroup(robotBase.liftSubsystem,robotBase.bucketSubsystem, robotBase.wristSubsystem, Lift.LiftPosition.HIGHBASKET));

        armController.getGamepadButton(GamepadKeys.Button.X)
                .and(new GamepadButton(armController, GamepadKeys.Button.RIGHT_BUMPER))
                .whenActive(new LiftGoToBasketCommandGroup(robotBase.liftSubsystem,robotBase.bucketSubsystem, robotBase.wristSubsystem, Lift.LiftPosition.LOWBASKET));

        armController.getGamepadButton(GamepadKeys.Button.B)
                .and(new GamepadButton(armController, GamepadKeys.Button.RIGHT_BUMPER))
                .toggleWhenActive(new HighChamberScoreCommandGroup(robotBase.liftSubsystem, robotBase.specimenGrabberSubsystem, robotBase.wristSubsystem),
                new LiftHome(robotBase.liftSubsystem));

        baseController.getGamepadButton(GamepadKeys.Button.START)
                .whenPressed(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand((()->robotBase.imu.resetYaw()))
                ));

        armController.getGamepadButton(GamepadKeys.Button.X)
                .and(new GamepadButton(armController, GamepadKeys.Button.LEFT_BUMPER))
                .whenActive(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.wristSubsystem.wristPickupPos())
                ));

        armController.getGamepadButton(GamepadKeys.Button.A)
                .and(new GamepadButton(armController, GamepadKeys.Button.LEFT_BUMPER))
                .whenActive(new IntakeReturnPositionCommandGroup(robotBase.liftSubsystem, robotBase.bucketSubsystem, robotBase.wristSubsystem, robotBase.zlideSubsystem));

        armController.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.bucketSubsystem.toggleBucket())
                ));

        armController.getGamepadButton(GamepadKeys.Button.B)
                .and(new GamepadButton(armController, GamepadKeys.Button.LEFT_BUMPER))
                .whenActive(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->robotBase.zlideSubsystem.zlideStartPosition())
                ));

        armController.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .toggleWhenPressed(new PickupSpecimenLineUpCommandGroup(robotBase.liftSubsystem, robotBase.specimenGrabberSubsystem, robotBase.wristSubsystem),
                        new PickupSpecimenGrabSpecimenCommandGroup(robotBase.specimenGrabberSubsystem, robotBase.liftSubsystem));

        baseController.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new OuttakeIntoBucketCommandGroup(robotBase.liftSubsystem, robotBase.bucketSubsystem, robotBase.zlideSubsystem, robotBase.wristSubsystem, robotBase.intakeSubsystem));
    }

    @Override
    public void loop() {
        double botHeading = robotBase.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        armController.readButtons();
        baseController.readButtons();


        double y = -gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y); // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x * Math.abs(gamepad1.left_stick_x);
        double rx = gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x);

        if (robotBase.zlideSubsystem.GetPostion() == 0) {
            rx = gamepad1.right_stick_x / 2;

        }

        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        if (bolFieldCentric) {
            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            dubDenominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            dubFrontLeftPower = (rotY + rotX + rx) / dubDenominator;
            dubBackLeftPower = (rotY - rotX + rx) / dubDenominator;
            dubFrontRightPower = (rotY - rotX - rx) / dubDenominator;
            dubBackRightPower = (rotY + rotX - rx) / dubDenominator;
        } else {
            dubDenominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            dubFrontLeftPower = (y + x + rx) / dubDenominator;
            dubBackLeftPower = (y - x + rx) / dubDenominator;
            dubFrontRightPower = (y - x - rx) / dubDenominator;
            dubBackRightPower = (y + x - rx) / dubDenominator;
        }

        robotBase.frontLeftMotor.setPower(dubFrontLeftPower);
        robotBase.backLeftMotor.setPower(dubBackLeftPower);
        robotBase.frontRightMotor.setPower(dubFrontRightPower);
        robotBase.backRightMotor.setPower(dubBackRightPower);
        telemetry.addData("Left Stick y", y);
        telemetry.addData("Left Stick x", x);
        telemetry.addData("Right Stick rx", rx);

        robotBase.intakeSubsystem.intakeSpeed(gamepad1.left_trigger / 2 + -1 * gamepad1.right_trigger / 2 + 0.5);

        telemetry.addData("lift motor", robotBase.liftSubsystem.getPosition());

        telemetry.addData("right trigger value", gamepad2.right_trigger);

        telemetry.addData("left trigger value", gamepad2.left_trigger);

        telemetry.addData("Lift motor power", robotBase.liftSubsystem.getPower());

        telemetry.addData("field centric", bolFieldCentric);

        telemetry.addData("Gyro", robotBase.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));

        telemetry.addData("Lift Limit Switch: ", robotBase.liftSubsystem.getSwitchState());

            CommandScheduler.getInstance().run();

    }
}