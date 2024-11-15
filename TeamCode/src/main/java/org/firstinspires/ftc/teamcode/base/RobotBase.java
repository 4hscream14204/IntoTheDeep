package org.firstinspires.ftc.teamcode.base;

import com.qualcomm.robotcore.hardware.DcMotor;

public class RobotBase {

    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor backLeftMotor;
    public DcMotor backRightMotor;

    frontLeftMotor = hwMap.dcMotor.get("left_front");
    backLeftMotor = hwMap.dcMotor.get("left_back");
    frontRightMotor = hwMap.dcMotor.get("right_front");
    backRightMotor = hwMap.dcMotor.get("right_back");
}
