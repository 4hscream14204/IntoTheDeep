package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

public class Extension extends SubsystemBase {

    DcMotor extendMotor;
    public DigitalChannel tsExtensionLimitSwitch;

    public enum ExtensionPosition{
        HOME (0);
        public final int height;
        ExtensionPosition(int high){
            this.height = high;
        }
    }

    double dblUpPower = 0;
    double dblDownPower = 0;
    boolean bolStopped = true;

    public ExtensionPosition enmExtensionPosition;

    public Extension(DcMotor extensionMotor) {
        extendMotor = extensionMotor;
        extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extendMotor.setTargetPosition(ExtensionPosition.HOME.height);
        extendMotor.setPower(0);
    }

    public void extendDown(double power) {
        extendMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extendMotor.setPower(power);
    }

    public void extendUp(double power) {
        extendMotor.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
        extendMotor.setPower(power);
    }


    public void goToPosition(ExtensionPosition enmTargetPosition) {
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
        if(isExtensionHome()){
            reset();
        }
        else{
            extendMotor.setPower(dblUpPower);
            extendMotor.setTargetPosition(extendMotor.getCurrentPosition());
            extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    public boolean isAtPosition(Shoulder.ShoulderPosition targetPosition){
        if(Math.abs(extendMotor.getCurrentPosition() - targetPosition.height) <= 10){
            return true;
        }
        return false;
    }

    public boolean isExtensionHome(){
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

    public double getPower(){
        return extendMotor.getPower();
    }
}
