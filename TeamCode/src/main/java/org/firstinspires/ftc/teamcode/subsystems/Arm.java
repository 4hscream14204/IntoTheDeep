package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.base.RobotBase;

public class Arm extends SubsystemBase {
    //public DcMotorEx dcArmMotor;
    public DcMotorSimple dcArmMotor;

    public double dblAmpLimit = 999999999;
    public boolean bolIsStopped = true;
    public int intCurrentPos;

    public Arm (DcMotorSimple armMotor/*DcMotorEx armMotor*/) {
        dcArmMotor = armMotor;
    }

    public void moveArm(RobotBase robotBase, double m_TriggerInput) {
        if (m_TriggerInput != 0) {
            dcArmMotor.setPower(m_TriggerInput);
        } else {

        }
    }

    public void stopInPlace () {
        if(bolIsStopped){
            return;
        }
        bolIsStopped = true;
        //dcArmMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        //intCurrentPos = dcArmMotor.getCurrentPosition();
        //dcArmMotor.setTargetPosition(intCurrentPos);
        setPower(0);
    }

    public void setPower(double m_power){
        /*if (isStalling()) {
            stopInPlace();
            return;
        }*/
        dcArmMotor.setPower(m_power);
    }

    /*public double getAmps () {
    //    return (dcArmMotor.getCurrent(CurrentUnit.AMPS));
    }*/

    /*public boolean isStalling () {
        return getAmps() > dblAmpLimit;
    }*/
}
