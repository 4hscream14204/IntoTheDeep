package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

public class Shoulder extends SubsystemBase {

    public DcMotor dcShoulderMotor;
    public DigitalChannel tsShoulderLimitSwitch;
    public double upPower = -0.3;
    public double downPower = 0.3;
    public boolean bolStoppedInPlace = true;

    public Shoulder(DcMotor conShoulderMotor, DigitalChannel conShoulderLimitSwitch) {
        dcShoulderMotor = conShoulderMotor;
    }

    public enum shoulderPosition{
        HOME (0);
        public final int height;
        shoulderPosition(int high){
            this.height = high;
        }
    }
    public shoulderPosition enmShoulderPosition;

    public void goToPosition(shoulderPosition enmTargetPosition){
        if(dcShoulderMotor.getCurrentPosition() < enmTargetPosition.height){
            dcShoulderMotor.setPower(downPower);
        }
        else if(dcShoulderMotor.getCurrentPosition() > enmTargetPosition.height){
            dcShoulderMotor.setPower(upPower);
        }
        else{
            stopInPlace();
            return;
        }
        dcShoulderMotor.setTargetPosition(enmTargetPosition.height);
        dcShoulderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        bolStoppedInPlace = false;
    }

    public void stopInPlace(){
        if(bolStoppedInPlace){
            return;
        }
        if(isShoulderDown()){
            reset();
        }
        else{
            dcShoulderMotor.setPower(upPower);
            dcShoulderMotor.setTargetPosition(dcShoulderMotor.getCurrentPosition());
            dcShoulderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    public int shoulderGetPosition(){
        return(dcShoulderMotor.getCurrentPosition());
    }

    public boolean isShoulderDown(){
       return tsShoulderLimitSwitch.getState();
    }

    public void reset(){
        dcShoulderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcShoulderMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dcShoulderMotor.setTargetPosition(0);
        dcShoulderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcShoulderMotor.setPower(0);
    }
}