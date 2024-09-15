package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Lift {

    public DcMotor liftMotor;

    public Lift (DcMotor conLiftMotor){
        liftMotor = conLiftMotor;
    }
    public void liftPosition(int position, double speed){
        liftMotor.setTargetPosition(position);
        liftMotor.setPower(speed);
    }
}
