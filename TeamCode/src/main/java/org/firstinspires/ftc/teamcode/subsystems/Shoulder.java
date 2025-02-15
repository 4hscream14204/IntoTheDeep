package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

public class Shoulder extends SubsystemBase {

    public DcMotor dcShoulderMotorLeft;
    public DcMotor dcShoulderMotorRight;
    public DigitalChannel tsShoulderLimitSwitch;
    public double dblUpPower = 1;
    public double dblDownPower = -0.5;
    public boolean bolStoppedInPlace = true;
    public int intCurrentPos;

    public enum ShoulderPosition{
        HOME (0),
        HIGHCHAMBER (1730),
        HIGHCHAMBERCLAMP (1975),
        NEWHIGHCHAMBER(1070),//2750, 3396
        LOWCHAMBER (680),
        LOWCHAMBERCLAMP (0),
        NEWLOWCHAMBER(1070),
        MAXPOSITION (1190),
        LOWBASKET (1070),
        HIGHBASKET (1070),
        TOGGLE (1060),
        AUTOPARK (1181),
        SECONDLEVELASCENT (1132);
        public final int height;
        ShoulderPosition(int high){
            this.height = high;
        }
    }

    public Shoulder(DcMotor conShoulderMotor, DcMotor rightShoulderMotor, DigitalChannel conShoulderLimitSwitch) {
        dcShoulderMotorLeft = conShoulderMotor;
        dcShoulderMotorRight = rightShoulderMotor;
        tsShoulderLimitSwitch = conShoulderLimitSwitch;
        dcShoulderMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcShoulderMotorLeft.setTargetPosition(0);
        dcShoulderMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dcShoulderMotorLeft.setPower(0);
        dcShoulderMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        enmShoulderPosition = ShoulderPosition.HOME;
        dcShoulderMotorRight.setDirection(DcMotor.Direction.REVERSE);
        dcShoulderMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcShoulderMotorRight.setTargetPosition(0);
        dcShoulderMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dcShoulderMotorRight.setPower(0);
        dcShoulderMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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
            dcShoulderMotorLeft.setPower(0);
            dcShoulderMotorRight.setPower(0);
            return;
        }
        if(isShoulderHome() && power == 0){
            reset();
            dcShoulderMotorLeft.setPower(0);
            dcShoulderMotorRight.setPower(0);
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
            dcShoulderMotorLeft.setPower(0.2);
            dcShoulderMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            dcShoulderMotorRight.setTargetPosition(intCurrentPos);
            dcShoulderMotorRight.setPower(0.2);
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
        dcShoulderMotorLeft.setPower(0);
        dcShoulderMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcShoulderMotorRight.setTargetPosition(0);
        dcShoulderMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcShoulderMotorRight.setPower(0);
    }

    public double getPower(){
        return dcShoulderMotorLeft.getPower();
    }
}
