package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class Arm extends SubsystemBase {

    public DcMotorEx armMotor;
    public boolean bolStoppedInPlace = true;
    public int intCurrentPos;
    public double dblInvalidDirection = 0;
    public double dblCurrentDirection;

    public double maxAmps = 6;

    public Arm (DcMotorEx m_armMotor) {
        armMotor = m_armMotor;
        armMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);;
        armMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        setPower(0);
    }

    public void move(double power) {
        armMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        armMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        setPower(power);

        bolStoppedInPlace = false;
    }

    public void stopInPlace(){
        if(bolStoppedInPlace){
            return;
        }
        bolStoppedInPlace = true;

        armMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        intCurrentPos = armMotor.getCurrentPosition();
        armMotor.setTargetPosition(intCurrentPos);
        armMotor.setPower(0.2);
    }

    public void reset(){
        bolStoppedInPlace = false;
        armMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        armMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        setPower(0);
    }

    public double getPower(){
        return armMotor.getPower();
    }

    public void setPower(double m_Power) {
        if (isStalling() || ((Math.abs(m_Power) / m_Power) == dblInvalidDirection)) {
            dblInvalidDirection = (Math.abs(m_Power) / m_Power);
            dblCurrentDirection = (Math.abs(m_Power) / m_Power);
            stopInPlace();
        } else {
            dblInvalidDirection = 0;
            dblCurrentDirection = (Math.abs(m_Power) / m_Power);
            armMotor.setPower(m_Power);
        }
    }

    public double getAMP() {
        return armMotor.getCurrent(CurrentUnit.AMPS);
    }

    public boolean isStalling(){
        return (getAMP() > maxAmps);
    }
}
