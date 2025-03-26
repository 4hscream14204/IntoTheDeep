package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class Shoulder extends SubsystemBase {

    public DcMotorEx dcShoulderMotorLeft;
    public DcMotorEx dcShoulderMotorRight;
    public DigitalChannel tsShoulderLimitSwitch;
    public double dblUpPower = 1;
    public double dblDownPower = -0.5;
    public boolean bolStoppedInPlace = true;
    public int intCurrentPos;

    public enum ShoulderPosition{
        HOME (0),
        HIGHCHAMBER (1730),
        HIGHCHAMBERCLAMP (1975),
        NEWHIGHCHAMBER(1060),//2750, 3396
        LOWCHAMBER (680),
        LOWCHAMBERCLAMP (0),
        NEWLOWCHAMBER(1070),
        MAXPOSITION (1190),
        LOWBASKET (1070),
        HIGHBASKET (1070),
        TOGGLE (1060),
        AUTOPARK (1181),
        SECONDLEVELASCENT (1160);
        public final int height;
        ShoulderPosition(int high){
            this.height = high;
        }
    }

    public  enum MaxAmps{
        MAXAMPLEFT (999999999),
        MAXAMPRIGHT(999999999);
        public final double max;
        MaxAmps(double cap){
            this.max = cap;
        }
    }

    public Shoulder(DcMotorEx conShoulderMotor, DcMotorEx rightShoulderMotor, DigitalChannel conShoulderLimitSwitch) {
        dcShoulderMotorLeft = conShoulderMotor;
        dcShoulderMotorRight = rightShoulderMotor;
        tsShoulderLimitSwitch = conShoulderLimitSwitch;
        dcShoulderMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcShoulderMotorLeft.setTargetPosition(0);
        dcShoulderMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);;
        dcShoulderMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        enmShoulderPosition = ShoulderPosition.HOME;
        dcShoulderMotorRight.setDirection(DcMotor.Direction.REVERSE);
        dcShoulderMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcShoulderMotorRight.setTargetPosition(0);
        dcShoulderMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);;
        dcShoulderMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setPower(0);

    }


    public ShoulderPosition enmShoulderPosition;

    public void goToPosition(ShoulderPosition enmTargetPosition){
        if(dcShoulderMotorLeft.getCurrentPosition() < enmTargetPosition.height){
            dcShoulderMotorLeft.setPower(dblUpPower);
            dcShoulderMotorRight.setPower(dblUpPower);
        }
        else if(dcShoulderMotorLeft.getCurrentPosition() > enmTargetPosition.height){
            dcShoulderMotorLeft.setPower(dblDownPower);
            dcShoulderMotorRight.setPower(dblDownPower);
        }
        else{
            stopInPlace();
            return;
        }
        enmShoulderPosition = enmTargetPosition;
        dcShoulderMotorLeft.setTargetPosition(enmTargetPosition.height);
        dcShoulderMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcShoulderMotorRight.setTargetPosition(enmTargetPosition.height);
        dcShoulderMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        bolStoppedInPlace = false;

    }

    public void goUpOrDown(double power){
        if(isShoulderHome() && power < 0){
            reset();
            setPower(0);
            return;
        }
        if(isShoulderHome() && power == 0){
            reset();
            setPower(0);
        }
        else{
            dcShoulderMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            dcShoulderMotorLeft.setPower(power);
            dcShoulderMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            dcShoulderMotorRight.setPower(power);

            bolStoppedInPlace = false;
        }

    }

    public void goUp(double power){
        if(dcShoulderMotorLeft.getCurrentPosition() > ShoulderPosition.MAXPOSITION.height){
            stopInPlace();
        }
        else {
            dcShoulderMotorLeft.setPower(power);
            dcShoulderMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            dcShoulderMotorRight.setPower(power);
            dcShoulderMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bolStoppedInPlace = false;
        }
    }

    public void goDown(double power){
       if(tsShoulderLimitSwitch.getState()){
            reset();
        }
        dcShoulderMotorLeft.setPower(power);
        dcShoulderMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dcShoulderMotorRight.setPower(power);
        dcShoulderMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bolStoppedInPlace = false;
    }

    public boolean isAtPosition(ShoulderPosition targetPosition){
        if(Math.abs(dcShoulderMotorLeft.getCurrentPosition() - targetPosition.height) <= 30){
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
            dcShoulderMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            intCurrentPos = dcShoulderMotorLeft.getCurrentPosition();
            dcShoulderMotorLeft.setTargetPosition(intCurrentPos);
            dcShoulderMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            dcShoulderMotorRight.setTargetPosition(intCurrentPos);
            setPower(0.2);
    }

    public int shoulderGetPosition(){
        return dcShoulderMotorLeft.getCurrentPosition();
    }

    public boolean isShoulderHome(){
       return tsShoulderLimitSwitch.getState();
    }

    public void reset(){
        bolStoppedInPlace = false;
        dcShoulderMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcShoulderMotorLeft.setTargetPosition(0);
        dcShoulderMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcShoulderMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcShoulderMotorRight.setTargetPosition(0);
        dcShoulderMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPower(0);
    }

    public double getPower(){
        return dcShoulderMotorLeft.getPower();
    }

    public void setPower(double m_Power) {
        if (isStalling()) {
            stopInPlace();
        } else {
            dcShoulderMotorLeft.setPower(m_Power);
            dcShoulderMotorRight.setPower(m_Power);
        }
    }

    public double getLeftAMP() {
        return dcShoulderMotorLeft.getCurrent(CurrentUnit.AMPS);
    }

    public double getRightAMP() {
        return dcShoulderMotorRight.getCurrent(CurrentUnit.AMPS);
    }

    public boolean isStalling(){
        return (getLeftAMP() > MaxAmps.MAXAMPLEFT.max || getRightAMP() > MaxAmps.MAXAMPRIGHT.max);
    }

    public void stopIfStalling(){
        if (isStalling()) {
            stopInPlace();
        }
    }
}
