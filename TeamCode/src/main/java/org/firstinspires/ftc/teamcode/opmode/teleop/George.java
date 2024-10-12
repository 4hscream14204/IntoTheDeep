package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.commands.IntakeCommandGroup;
import org.firstinspires.ftc.teamcode.commands.IntakeTransferCommandGroup;
import org.firstinspires.ftc.teamcode.commands.LiftHome;
import org.firstinspires.ftc.teamcode.subsystems.Bucket;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.firstinspires.ftc.teamcode.subsystems.Zlide;

@TeleOp(name="run George")
public class George extends OpMode{

    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;
    IMU imu;

    Intake intakeSubsystem;
    Bucket bucketSubsystem;
    Zlide zlideSubsystem;
    Wrist wristSubsystem;
    Lift liftSubsystem;


    GamepadEx armController;
    GamepadEx baseController;

    boolean fieldCentric  = true;
    double denominator;
    double frontLeftPower;
    double backLeftPower;
    double frontRightPower;
    double backRightPower;

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        Gamepad.LedEffect rgbEffect = new Gamepad.LedEffect.Builder()
                .addStep(1, 0, 0, 500) // Show red for 250ms
                .addStep(0, 1, 0, 500) // Show green for 250ms
                .addStep(0, 0, 1, 500) // Show blue for 250ms
                .addStep(1, 1, 1, 500) // Show white for 250ms
                .build();
        gamepad1.runLedEffect(rgbEffect);
        gamepad1.rumble(1000);

        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);

        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        armController = new GamepadEx (gamepad2);
        baseController = new GamepadEx(gamepad1);

        intakeSubsystem = new Intake(hardwareMap.servo.get("intakeServo"));
        bucketSubsystem = new Bucket(hardwareMap.servo.get("bucketServo"));
        zlideSubsystem = new Zlide(hardwareMap.servo.get("zlideServo"));
        wristSubsystem = new Wrist(hardwareMap.servo.get("transferServo"));
        liftSubsystem = new Lift(hardwareMap.dcMotor.get("slideMotor"));

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        /*armController.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(()->CommandScheduler.getInstance().schedule(
                        new InstantCommand(()->bucketSubsystem.toggleBucket())
                        )); */

        armController.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(new IntakeCommandGroup(zlideSubsystem, wristSubsystem));

        armController.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(new IntakeTransferCommandGroup(zlideSubsystem, wristSubsystem, bucketSubsystem));

        armController.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new LiftHome(liftSubsystem));

        baseController.getGamepadButton((GamepadKeys.Button.Y))
                .whenPressed(()->CommandScheduler.getInstance().schedule(
                new InstantCommand(()->fieldCentric = !fieldCentric)
        ));

    }

    @Override
    public void loop() {
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        armController.readButtons();
        baseController.readButtons();


        double y = -gamepad1.left_stick_y  * Math.abs (gamepad1.left_stick_y); // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x * Math.abs (gamepad1.left_stick_x);
        double rx = gamepad1.right_stick_x * Math.abs (gamepad1.right_stick_x);

        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        if (fieldCentric) {
            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            frontLeftPower = (rotY + rotX + rx) / denominator;
            backLeftPower = (rotY - rotX + rx) /denominator;
            frontRightPower = (rotY - rotX - rx) / denominator;
            backRightPower = (rotY + rotX - rx) / denominator;
        } else {
            denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            frontLeftPower = (y + x + rx) / denominator;
            backLeftPower = (y - x + rx) / denominator;
            frontRightPower = (y - x - rx) / denominator;
            backRightPower = (y + x - rx) / denominator;
        }

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);
        telemetry.addData("Left Stick y", y);
        telemetry.addData("Left Stick x", x);
        telemetry.addData("Right Stick rx", rx);

        if (gamepad1.options) {
            imu.resetYaw();
        }

        if (armController.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
            wristSubsystem.wristPickupPos();
        }


        if (armController.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {
            wristSubsystem.wristTransferPos();
        }
        /*
        if(armController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) >= 0.1){
            intakeSubsystem.intakeSpeed((gamepad2.right_trigger));
        }
        if(armController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) >= 0.1){
            intakeSubsystem.intakeSpeed((gamepad2.left_trigger));
        }
        */
        if (armController.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)) {
            bucketSubsystem.toggleBucket();
        }

        intakeSubsystem.intakeSpeed( gamepad2.left_trigger/2 + -1 * gamepad2.right_trigger/2 + 0.5);

        /*if (armController.wasJustPressed(GamepadKeys.Button.DPAD_UP)){

            zlideSubsystem.zlideExtendPosition();

        }
*/
        /*if (armController.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)){

            zlideSubsystem.zlideBucketPosition();

        } */

        if (armController.wasJustPressed((GamepadKeys.Button.DPAD_RIGHT))){
            zlideSubsystem.zlideStartPosition();
        }

        /*if (gamepad2.right_stick_y>0.1){
            liftSubsystem.goDown(gamepad2.right_stick_y);
        }

        if (gamepad2.right_stick_y<-0.1){
            liftSubsystem.goUp(gamepad2.right_stick_y);
        }
/*
        if (gamepad2.right_trigger < 0.1 && gamepad2.left_trigger < 0.1) {
            liftSubsystem.stop();
        }*/

        if (gamepad2.y) {
            liftSubsystem.highBasket();
        }

        if (gamepad2.b) {
            liftSubsystem.highChamber();
        }

        /*if (gamepad2.a) {
            liftSubsystem.home();
        }*/

        telemetry.addData("lift motor", liftSubsystem.getPosition());

        telemetry.addData("right trigger value", gamepad2.right_trigger);

        telemetry.addData("left trigger value", gamepad2.left_trigger);

        telemetry.addData("Lift motor power", liftSubsystem.getPower());

        telemetry.addData("feild centric", fieldCentric);

        telemetry.addData("Gyro", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));

        CommandScheduler.getInstance().run();
    }
}