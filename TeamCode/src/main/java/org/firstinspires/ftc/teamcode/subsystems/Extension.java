package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

public class Extension extends SubsystemBase {

    DcMotor extendMotor;
    public DigitalChannel tsExtensionLimitSwitch;

    public enum extensionPosition{
        HOME (0);
        public final int height;
        extensionPosition(int high){
            this.height = high;
        }
    }

    double dblUpPower = 0;
    double dblDownPower = 0;
    boolean bolStopped = true;

    public Extension(DcMotor extensionMotor) {
        extendMotor = extensionMotor;
        extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extendMotor.setTargetPosition(extensionPosition.HOME.height);
        extendMotor.setPower(dblDownPower);
    }

    public void extendDown() {
        extendMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extendMotor.setPower(dblDownPower);
    }

    public void extendUp() {
        extendMotor.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
        extendMotor.setPower(dblUpPower);
    }

    public extensionPosition enmExtensionPosition;

    public void extensionGoToPosition(extensionPosition enmTargetPosition) {
        if(extendMotor.getCurrentPosition() < enmTargetPosition.height){
            enmExtensionPosition = enmTargetPosition;
            extendMotor.setPower(dblDownPower);
        }
        else if(extendMotor.getCurrentPosition() > enmTargetPosition.height){
            enmExtensionPosition = enmTargetPosition;
            extendMotor.setPower(dblUpPower);
        }
        else{
            stopInPlace();
            return;
        }
        extendMotor.setTargetPosition(enmTargetPosition.height);
        extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        bolStopped = false;
    }

    public void stopInPlace(){
        if(bolStopped){
            return;
        }
        bolStopped = true;
        if(isExtensionDown()){
            reset();
        }
        else{
            extendMotor.setPower(dblUpPower);
            extendMotor.setTargetPosition(extendMotor.getCurrentPosition());
            extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    public boolean isAtPosition(Shoulder.shoulderPosition targetPosition){
        if(Math.abs(extendMotor.getCurrentPosition() - targetPosition.height) <= 10){
            return true;
        }
        return false;
    }

    public boolean isExtensionDown(){
        return tsExtensionLimitSwitch.getState();
    }

    public void reset(){
        extendMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extendMotor.setTargetPosition(0);
        extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extendMotor.setPower(0);
    }

    public int extensionGetPosition(){
        return(extendMotor.getCurrentPosition());
    }
}
