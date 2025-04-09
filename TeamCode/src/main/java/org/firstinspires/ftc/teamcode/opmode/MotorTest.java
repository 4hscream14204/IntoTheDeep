package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.RobotBase;

@TeleOp(name = ("Motor Config Test"))
public class MotorTest extends OpMode {
    public RobotBase robotBase;
    @Override
    public void init(){
        robotBase = new RobotBase(hardwareMap);
    }

    public void loop(){
        if(gamepad1.a){
            robotBase.chassisSubsystem.frontLeftMotor.setPower(1);
        }
        else if(gamepad1.b){
            robotBase.chassisSubsystem.frontRightMotor.setPower(1);
        }
        else if(gamepad1.x){
            robotBase.chassisSubsystem.backLeftMotor.setPower(1);
        }
        else if(gamepad1.y){
            robotBase.chassisSubsystem.backRightMotor.setPower(1);
        }
        else{
            robotBase.chassisSubsystem.frontLeftMotor.setPower(0);
            robotBase.chassisSubsystem.frontRightMotor.setPower(0);
            robotBase.chassisSubsystem.backRightMotor.setPower(0);
            robotBase.chassisSubsystem.backLeftMotor.setPower(0);
        }
        telemetry.addLine("Front Left Motor: A");
        telemetry.addLine("Front Right Motor: B");
        telemetry.addLine("Back Left Motor: X");
        telemetry.addLine("Bacl Right Motor: Y");
        telemetry.update();
    }
}
