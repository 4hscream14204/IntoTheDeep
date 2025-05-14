package org.firstinspires.ftc.teamcode.base;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

public class RobotBase {

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    public DcMotorEx armMotor;
    public CRServo intake;
    public Servo wrist;

    public Intake intakeSubsystem;
    public Wrist wristSubsystem;
    public Arm armSubsystem;
    public Chassis chassisSubsystem;

    public RobotBase(HardwareMap hwMap) {
        intakeSubsystem = new Intake(hwMap.crservo.get("intakeServo"));
        wristSubsystem = new Wrist(hwMap.servo.get("wristServo"));
        armSubsystem = new Arm (hwMap.get(DcMotorEx.class, "armMotor"));
        chassisSubsystem = new Chassis(hwMap.dcMotor.get("left_front"), hwMap.dcMotor.get("right_front"), hwMap.dcMotor.get("left_back"), hwMap.dcMotor.get("right_back"));

    }


}
