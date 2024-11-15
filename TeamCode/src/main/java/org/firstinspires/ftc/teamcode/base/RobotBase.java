package org.firstinspires.ftc.teamcode.base;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class RobotBase {

    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor backLeftMotor;
    public DcMotor backRightMotor;

    public Intake intakeSubsystem;
    public Claw clawSubsystem;
    public Elbow elbowSubsystem;
    public Extension extensionSubsystem;
    public Shoulder shoulderSubsystem;
    public Wrist wristSubsystem;


    public RobotBase(HardwareMap hwMap) {
        frontLeftMotor = hwMap.dcMotor.get("left_front");
        backLeftMotor = hwMap.dcMotor.get("left_back");
        frontRightMotor = hwMap.dcMotor.get("right_front");
        backRightMotor = hwMap.dcMotor.get("right_back");

    }
}
