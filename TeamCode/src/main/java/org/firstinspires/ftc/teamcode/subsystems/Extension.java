package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

public class Extension extends SubsystemBase {

    public DcMotor extendMotor;
    public DigitalChannel tsExtensionLimitSwitch;

    public enum ExtensionPosition{
        HOME (0),
        MAXSHOULDERDOWNPOSITION(-2390),
        MAXSHOULDERUPPOSITION (-4400),
        LOWBUCKET (-1530),
        HIGHBUCKET (-4250),
        LOWCHAMBER (-700),
        LOWCHAMBERCLAMP(0),
        NEWLOWCHAMBER (-750),
        NEWLOWCHAMBERCLAMP (0),
        HIGHCHAMBER (-1975),
        HIGHCHAMBERCLAMP (-1425),
        NEWHIGHCHAMBER (-2550),//-1870
        NEWHIGHCHAMBERCLAMP (-1600),//-1150
        SECONDLEVELASCENT (-4400),
        SECONDLEVELASCENTPULL (-2275),
        SPECIMENPICKUP(-750);
        public final int height;
        ExtensionPosition(int high){
            this.height = high;
        }
    }

    public double dblUpPower = -1;
    public double dblDownPower = 1;
    public boolean bolStopped = true;
    public int intCurrentPos;
    public int intMaxPosition;

    public ExtensionPosition enmExtensionPosition;

    public Extension(DcMotor extensionMotor, DigitalChannel conTsExtensionLimitSwitch) {
        extendMotor = extensionMotor;
        tsExtensionLimitSwitch = conTsExtensionLimitSwitch;
        extendMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendMotor.setTargetPosition(0);
        extendMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extendMotor.setPower(0);
        extendMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        enmExtensionPosition = ExtensionPosition.HOME;
    }

    public void extend(double power) {
        if(extendMotor.getCurrentPosition() < intMaxPosition){
            stopInPlace();
        }
        if(isExtensionHome() && power > 0){
            extendMotor.setPower(0);
            reset();
            return;
        }
        else {
            extendMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            extendMotor.setPower(power);
            bolStopped = false;
        }
    }

    public void extendForward(double power) {
        if(extendMotor.getCurrentPosition() < intMaxPosition){
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
        else if(isAtPosition(enmTargetPosition)){
            stopInPlace();
            return;
        }
        extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extendMotor.setPower(dblUpPower);
        extendMotor.setTargetPosition(enmTargetPosition.height);

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

    public int getTargetPosition(){
        return extendMotor.getTargetPosition();
    }

    public boolean isPastMaxPosition(){
        return extensionGetPosition() <= intMaxPosition;
    }
}
