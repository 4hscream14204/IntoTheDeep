package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Shoulder extends SubsystemBase {

    public DcMotor shoulderMotor;
    public DigitalChannel tsShoulderLimitSwitch;
    public double upPower = -0.3;
    public double downPower = 0.3;

    public Shoulder(DcMotor conShoulderMotor, DigitalChannel conShoulderLimitSwitch) {
        shoulderMotor = conShoulderMotor;
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
        if(shoulderMotor.getCurrentPosition() < enmTargetPosition.height){
            shoulderMotor.setPower(downPower);
        }
        else if(shoulderMotor.getCurrentPosition() > enmTargetPosition.height){
            shoulderMotor.setPower(upPower);
        }
        else{
            return;
        }
    }

    public int shoulderGetPosition(){
        return(shoulderMotor.getCurrentPosition());
    }

    public boolean isShoulderDown(){
       return tsShoulderLimitSwitch.getState();
    }
}
