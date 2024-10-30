package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

public class Lift extends SubsystemBase {

    public DcMotor liftMotor;
    public DigitalChannel tsLimitSwitch;
    public double upPower= -1;
    public double downPower = 0.9;
    public boolean stopped = true;
    public enum LiftPosition{
        HOME (15),
        PICKUP (-227),
        HIGHCHAMBERSTART (-1930),
        HIGHCHAMBERCLAMP (-1620),
        HIGHBASKET (-4200),
        LOWBASKET (-2600);
        public final int height;
        LiftPosition(int high){
            this.height = high;
        }
    }
    public LiftPosition enmLiftPosition;

    public Lift (DcMotor conLiftMotor, DigitalChannel conLimitSwitch){
        liftMotor = conLiftMotor;
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setTargetPosition(0);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor.setPower(0);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        tsLimitSwitch = conLimitSwitch;
        enmLiftPosition = LiftPosition.HOME;
    }
    public void goToPosition(LiftPosition enmTargetPosition){
        if(liftMotor.getCurrentPosition()<enmTargetPosition.height){
            liftMotor.setPower(downPower);
            if (liftMotor.getCurrentPosition() >= enmTargetPosition.height) {
                stop();
            }
        }
        else if(liftMotor.getCurrentPosition()>enmTargetPosition.height){
            liftMotor.setPower(upPower);

            if (liftMotor.getCurrentPosition() <= enmTargetPosition.height) {
                stop();
            }
        }
        liftMotor.setTargetPosition(enmTargetPosition.height);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        stopped = false;
    }
    public void goDown(double power){
        if(tsLimitSwitch.getState()){
            reset();
            return;
        }
        if(liftMotor.getCurrentPosition()>LiftPosition.HOME.height - 10){
            stop();
        }
        else {
            liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            liftMotor.setPower(power * 1);
            stopped=false;
        }
    }

    public void goUp(double power){
        if(liftMotor.getCurrentPosition()<(LiftPosition.HIGHBASKET.height)){
            stop();
        }
        else{
            liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            liftMotor.setPower(power * -1);
            stopped=false;
            liftMotor.setPower(power * -1);
        }
    }

    public void stop () {

        if(stopped) {
            return;
        }
        stopped=true;
        if (tsLimitSwitch.getState()) {
            reset();
        }
        else {
            liftMotor.setPower(-0.3);
            liftMotor.setTargetPosition(liftMotor.getCurrentPosition());
            liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    public boolean isHome(){
        if(liftMotor.getCurrentPosition()<= LiftPosition.HOME.height -30){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isHighBasket(){
        if(liftMotor.getCurrentPosition()<= LiftPosition.HIGHBASKET.height +250){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isLowBasket(){
        if(liftMotor.getCurrentPosition()<= LiftPosition.LOWBASKET.height +320 && liftMotor.getCurrentPosition()> LiftPosition.LOWBASKET.height +280){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isAtBasket(LiftPosition basketTarget){
        if(Math.abs(liftMotor.getCurrentPosition() - basketTarget.height) <= 10){
            return true;
        }
        return false;
    }

    public boolean isAtHighChamber(){
        if(liftMotor.getCurrentPosition() <= -1930 && liftMotor.getCurrentPosition() >= LiftPosition.HIGHCHAMBERSTART.height -10){
            return true;
        }
        return false;
    }

    public int getPosition() {
        return liftMotor.getCurrentPosition();
    }

    public double getPower() {
        return liftMotor.getPower();
    }
    public void reset(){
            liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            liftMotor.setTargetPosition(0);
            liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            liftMotor.setPower(0);
    }
    public boolean getSwitchState(){
       return tsLimitSwitch.getState();
    }
}
