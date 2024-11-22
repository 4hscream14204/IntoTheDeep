package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

public class Shoulder extends SubsystemBase {

    public DcMotor dcShoulderMotor;
    public DigitalChannel tsShoulderLimitSwitch;
    public double dblUpPower = -0.3;
    public double dblDownPower = 0.3;
    public boolean bolStoppedInPlace = true;

    public enum ShoulderPosition{
        HOME (0),
        HIGHCHAMBER (0),
        BASKET (0);
        public final int height;
        ShoulderPosition(int high){
            this.height = high;
        }
    }

    public Shoulder(DcMotor conShoulderMotor, DigitalChannel conShoulderLimitSwitch) {
        dcShoulderMotor = conShoulderMotor;
        dcShoulderMotor.setPower(0);
    }


    public ShoulderPosition enmShoulderPosition;

    public void goToPosition(ShoulderPosition enmTargetPosition){
        if(dcShoulderMotor.getCurrentPosition() < enmTargetPosition.height){
            dcShoulderMotor.setPower(dblDownPower);
        }
        else if(dcShoulderMotor.getCurrentPosition() > enmTargetPosition.height){
            dcShoulderMotor.setPower(dblUpPower);
        }
        else{
            stopInPlace();
            return;
        }
        enmShoulderPosition = enmTargetPosition;
        dcShoulderMotor.setTargetPosition(enmTargetPosition.height);
        dcShoulderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        bolStoppedInPlace = false;
    }

    public void goUp(double power){
        dcShoulderMotor.setPower(power * -1);
        dcShoulderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bolStoppedInPlace = false;
    }

    public void goDown(double power){
        dcShoulderMotor.setPower(power);
        dcShoulderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bolStoppedInPlace = false;
    }

    public boolean isAtPosition(ShoulderPosition targetPosition){
        if(Math.abs(dcShoulderMotor.getCurrentPosition() - targetPosition.height) <= 10){
            return true;
        }
        return false;
    }

    public void stopInPlace(){
        if(bolStoppedInPlace){
            return;
        }
        bolStoppedInPlace = true;
        if(isShoulderHome()){
            reset();
        }
        else{
            dcShoulderMotor.setPower(dblUpPower);
            dcShoulderMotor.setTargetPosition(dcShoulderMotor.getCurrentPosition());
            dcShoulderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    public int shoulderGetPosition(){
        return(dcShoulderMotor.getCurrentPosition());
    }

    public boolean isShoulderHome(){
       return tsShoulderLimitSwitch.getState();
    }

    public void reset(){
        dcShoulderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcShoulderMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcShoulderMotor.setTargetPosition(0);
        dcShoulderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcShoulderMotor.setPower(0);
    }

    public double getPower(){
        return dcShoulderMotor.getPower();
    }
}
