package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

public class Extension extends SubsystemBase {

    public DcMotor extendLeftMotor;
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
        SECONDLEVELASCENTPULL (-3400),
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

    public Extension(DcMotor m_extensionLeftMotor, DigitalChannel m_TsExtensionLimitSwitch) {
        extendLeftMotor = m_extensionLeftMotor;
        tsExtensionLimitSwitch = m_TsExtensionLimitSwitch;
        extendLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setTargetPosition(0);
        extendLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extendLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        enmExtensionPosition = ExtensionPosition.HOME;
    }

    public void setPower(double power){
        extendLeftMotor.setPower(power);
    }

    public void setTargetPosition(int position){
        extendLeftMotor.setTargetPosition(position);
    }

    public void extend(double power) {
        if(extensionGetPosition() < intMaxPosition){
            stopInPlace();
        }
        if(isExtensionHome() && power > 0){
            setPower(0);
            reset();
            return;
        }
        else {
            extendLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            setPower(power);
            bolStopped = false;
        }
    }

    public void extendForward(double power) {
        if(extensionGetPosition() < intMaxPosition){
            stopInPlace();
        }
        else {
            extendLeftMotor.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
            setPower(power * -1);
            bolStopped = false;
        }
    }


    public void goToPosition(ExtensionPosition enmTargetPosition) {
       if(extensionGetPosition() < enmTargetPosition.height){
            enmExtensionPosition = enmTargetPosition;
            setPower(dblDownPower);
        }
        else if(extensionGetPosition() > enmTargetPosition.height){
            enmExtensionPosition = enmTargetPosition;
            setPower(dblUpPower);
        }
        else if(isAtPosition(enmTargetPosition)){
            stopInPlace();
            return;
        }
        extendLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPower(dblUpPower);
        setTargetPosition(enmTargetPosition.height);

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
            extendLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            intCurrentPos = extensionGetPosition();
            setTargetPosition(intCurrentPos);
            setPower(-0.1);
        }
    }

    public boolean isAtPosition(ExtensionPosition targetPosition){
        if(Math.abs(extensionGetPosition() - targetPosition.height) <= 50){
            return true;
        }
        return false;
    }

    public boolean isExtensionHome(){
        return tsExtensionLimitSwitch.getState();
    }

    public void reset(){
        bolStopped = false;
        extendLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setTargetPosition(0);
        extendLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPower(0);
    }

    public int extensionGetPosition(){
        return extendLeftMotor.getCurrentPosition();
    }

    public double getPower(){
        return extendLeftMotor.getPower();
    }

    public int getTargetPosition(){
        return extendLeftMotor.getTargetPosition();
    }

    public boolean isPastMaxPosition(){
        return extensionGetPosition() <= intMaxPosition;
    }
}
