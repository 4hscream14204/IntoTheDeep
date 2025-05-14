package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class Arm extends SubsystemBase {

    public DcMotorEx armMotor;
    public boolean bolStoppedInPlace = true;
    public int intCurrentPos;

    public double maxAmps = 999999;

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
        armMotor.setPower(power);
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
        setPower(0.2);
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
        if (isStalling()) {
            stopInPlace();
        } else {
            armMotor.setPower(m_Power);
        }
    }

    public double getAMP() {
        return armMotor.getCurrent(CurrentUnit.AMPS);
    }

    public boolean isStalling(){
        return (getAMP() > maxAmps);
    }

    public void stopIfStalling(){
        if (isStalling()) {
            stopInPlace();
        }
    }


}
