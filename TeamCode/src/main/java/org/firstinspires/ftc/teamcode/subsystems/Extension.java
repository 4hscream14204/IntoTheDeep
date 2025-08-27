package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;

public class Extension extends SubsystemBase {

    public DcMotor extendLeftMotor;
    public DcMotor extendMiddleMotor;
    public DcMotor extendRightMotor;
    public DigitalChannel tsExtensionLimitSwitch;

    public enum ExtensionPosition{
        HOME (0),
        MAXSHOULDERDOWNPOSITION(-800),
        MAXSHOULDERUPPOSITION (-1906),
        LOWBUCKET (-663),
        HIGHBUCKET (-1841),
        LOWCHAMBER (-500),
        NEWLOWCHAMBERCLAMP (0),
        HIGHCHAMBER (-1105),//-1870
        HIGHCHAMBERCLAMP (-690),//-1150
        SECONDLEVELASCENT (-1001/*-2933*/),
        SECONDLEVELASCENTPULL (-32/*-2266*/),
        AUTOPREINTAKESAMPLE(-700),
        SPECIMENPICKUP(-325);
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

    public Extension(DcMotor m_extensionLeftMotor, DcMotor m_extensionMiddleMotor,DcMotor m_extensionRightMotor, DigitalChannel m_TsExtensionLimitSwitch) {
        extendLeftMotor = m_extensionLeftMotor;
        extendMiddleMotor = m_extensionMiddleMotor;
        extendRightMotor = m_extensionRightMotor;
        tsExtensionLimitSwitch = m_TsExtensionLimitSwitch;
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setTargetPosition(0);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extendLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extendMiddleMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extendRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        enmExtensionPosition = ExtensionPosition.HOME;
        extendLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        extendMiddleMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setPower(double power){
        extendLeftMotor.setPower(power);
        extendMiddleMotor.setPower(power);
        extendRightMotor.setPower(power);
    }

    public void setTargetPosition(int position){
        extendLeftMotor.setTargetPosition(position);
        extendMiddleMotor.setTargetPosition(position);
        extendRightMotor.setTargetPosition(position);
    }

    public void setMode(DcMotor.RunMode mode){
        extendLeftMotor.setMode(mode);
        extendMiddleMotor.setMode(mode);
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
        return (Math.abs(extensionGetPosition() - targetPosition.height) <= 50);
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

    public double getLeftPower(){
        return extendLeftMotor.getPower();
    }

    public double getRightPower(){return extendRightMotor.getPower();}

    public double getMiddlePower(){return extendMiddleMotor.getPower();}

    public int getMiddlePosition(){return extendMiddleMotor.getCurrentPosition();}

    public int getRightPosition(){return extendRightMotor.getCurrentPosition();}

    public int getLeftPosition(){return extendLeftMotor.getCurrentPosition();}

    public int getTargetPosition(){
        return extendLeftMotor.getTargetPosition();
    }

    public boolean isPastMaxPosition(){
        return extensionGetPosition() <= intMaxPosition;
    }
}