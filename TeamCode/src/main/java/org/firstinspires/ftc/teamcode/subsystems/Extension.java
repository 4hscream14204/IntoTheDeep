package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;

public class Extension extends SubsystemBase {

    public DcMotor extendLeftMotor;
    public DcMotor extendRightMotor;
    public DigitalChannel tsExtensionLimitSwitch;

    public enum ExtensionPosition{
        HOME (0),
        MAXSHOULDERDOWNPOSITION(-1593),
        MAXSHOULDERUPPOSITION (-2933),
        LOWBUCKET (-1020),
        HIGHBUCKET (-2833),
        LOWCHAMBER (-500),
        NEWLOWCHAMBERCLAMP (0),
        HIGHCHAMBER (-1700),//-1870
        HIGHCHAMBERCLAMP (-1250),//-1150
        SECONDLEVELASCENT (-2933),
        SECONDLEVELASCENTPULL (-2266),
        SPECIMENPICKUP(-500);
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

    public Extension(DcMotor m_extensionLeftMotor, DcMotor m_extentionRightMotor, DigitalChannel m_TsExtensionLimitSwitch) {
        extendLeftMotor = m_extensionLeftMotor;
        extendRightMotor = m_extentionRightMotor;
        tsExtensionLimitSwitch = m_TsExtensionLimitSwitch;
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setTargetPosition(0);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extendLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extendRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        enmExtensionPosition = ExtensionPosition.HOME;
    }

    public void setPower(double power){
        extendLeftMotor.setPower(power);
        extendRightMotor.setPower(power);
    }

    public void setTargetPosition(int position){
        extendLeftMotor.setTargetPosition(position);
        extendRightMotor.setTargetPosition(position);
    }

    public void setMode(DcMotor.RunMode mode){
        extendLeftMotor.setMode(mode);
        extendRightMotor.setMode(mode);
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
            setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            setPower(power);
            bolStopped = false;
        }
    }

    public void extendForward(double power) {
        if(extensionGetPosition() < intMaxPosition){
            stopInPlace();
        }
        else {
            setMode((DcMotor.RunMode.RUN_USING_ENCODER));
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
        setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //setPower(dblUpPower);
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
            setMode(DcMotor.RunMode.RUN_TO_POSITION);
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
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setTargetPosition(0);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);
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
