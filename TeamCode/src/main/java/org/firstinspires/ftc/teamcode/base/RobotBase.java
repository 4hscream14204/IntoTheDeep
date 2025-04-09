package org.firstinspires.ftc.teamcode.base;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Claw;

public class RobotBase {

    public DcMotor frontLeftMotor;
    public DcMotor fronRightMotor;
    public DcMotor backLeftMotor;
    public DcMotor backRightmotor;

    public Arm armsubsystem;
    public Claw clawsubsystem;

    public RobotBase(HardwareMap hwMap) {

        armsubsystem = new Arm(hwMap.get(DcMotorEx.class, "ArmMotor"));
        clawsubsystem = new Claw(hwMap.servo.get("ClawServo"));
    }


}
