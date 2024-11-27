package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

public class Extension extends SubsystemBase {

    DcMotor extendMotor;
    public DigitalChannel tsExtensionLimitSwitch;

    public enum ExtensionPosition{
        HOME (0),
        MAXPOSITION (-2330);
        public final int height;
        ExtensionPosition(int high){
            this.height = high;
        }
    }

    public double dblUpPower = -0.3;
    public double dblDownPower = 0.3;
    public boolean bolStopped = true;
    public int intCurrentPos;

    public ExtensionPosition enmExtensionPosition;

    public Extension(DcMotor extensionMotor, DigitalChannel conTsExtensionLimitSwitch) {
        extendMotor = extensionMotor;
        tsExtensionLimitSwitch = conTsExtensionLimitSwitch;
        /*extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extendMotor.setTargetPosition(ExtensionPosition.HOME.height);*/
        extendMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendMotor.setTargetPosition(0);
        extendMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extendMotor.setPower(0);
        extendMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        enmExtensionPosition = ExtensionPosition.HOME;
    }

    public void extendBack(double power) {
        if(isExtensionHome()){
            reset();
        }
        //else {
            extendMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            extendMotor.setPower(power);
            bolStopped = false;
      //  }
    }

    public void extendForward(double power) {
        if(extendMotor.getCurrentPosition() < ExtensionPosition.MAXPOSITION.height){
            stopInPlace();
        }
        else {
            extendMotor.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
            extendMotor.setPower(power * -1);
            bolStopped = false;
        }
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
            extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            intCurrentPos = extendMotor.getCurrentPosition();
            extendMotor.setTargetPosition(intCurrentPos);
            extendMotor.setPower(-0.1);
        }
    }

    public boolean isAtPosition(ExtensionPosition targetPosition){
        if(Math.abs(extendMotor.getCurrentPosition() - targetPosition.height) <= 10){
            return true;
        }
        return false;
    }

    public boolean isExtensionHome(){
        return tsExtensionLimitSwitch.getState();
    }

    public void reset(){
        bolStopped = false;
        extendMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendMotor.setTargetPosition(0);
        extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extendMotor.setPower(0);
    }

    public int extensionGetPosition(){
        return extendMotor.getCurrentPosition();
    }

    public double getPower(){
        return extendMotor.getPower();
    }
}
