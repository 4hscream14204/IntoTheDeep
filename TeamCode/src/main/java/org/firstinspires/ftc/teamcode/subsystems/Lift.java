package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Lift extends SubsystemBase {

    public DcMotor liftMotor;
    public double speed = 0;
    public int home = 0;
    public int highBasket = -4000;
    public int highChamber = -1700;
    public int pickup = -100;
    public double upPower= -0.5;
    public double downPower = 0.4;
    public boolean stopped = true;

    public Lift (DcMotor conLiftMotor){
        liftMotor = conLiftMotor;
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setTargetPosition(0);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor.setPower(0);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void goDown(double power){
        if(liftMotor.getCurrentPosition()>home-10){
            stop();
        }
        else {
            liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            liftMotor.setPower(power * 1);
            stopped=false;
        }
    }

    public void goUp(double power){
        if(liftMotor.getCurrentPosition()<(highBasket)){
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
        if (liftMotor.getCurrentPosition() >=-10) {
            liftMotor.setPower(0);
            liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        else {
            liftMotor.setPower(-0.3);
            liftMotor.setTargetPosition(liftMotor.getCurrentPosition());
            liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }
    public void home (){
        liftMotor.setPower(downPower);
        liftMotor.setTargetPosition(home);

        if (liftMotor.getCurrentPosition() >= home) {
            stop();
        }
    }
    public void highBasket(){
        liftMotor.setPower(upPower);
        liftMotor.setTargetPosition(highBasket);

        if (liftMotor.getCurrentPosition() <= highBasket) {
            stop();
        }
    }

    public void highChamber(){
        if(liftMotor.getCurrentPosition()<highChamber){
            liftMotor.setPower(downPower);
        }
        else if(liftMotor.getCurrentPosition()>highChamber){
            liftMotor.setPower(upPower);

            if (liftMotor.getCurrentPosition() <= highChamber) {

            }
        }
        liftMotor.setTargetPosition(highChamber);
    }

    public void pickup(){
        if(liftMotor.getCurrentPosition()<pickup){
            liftMotor.setPower(downPower);
        }
        else if(liftMotor.getCurrentPosition()>pickup){
            liftMotor.setPower(upPower);
        }
        liftMotor.setTargetPosition(pickup);
    }

    public void checkPosition(){
        if(liftMotor.getCurrentPosition()>(home - 30)){
            liftMotor.setPower(0);
        }
    }

    public boolean isHome(){
        if(liftMotor.getCurrentPosition()<=-15){
            return false;
        }
        else {
            return true;
        }
    }

    public int getPosition() {
        return liftMotor.getCurrentPosition();
    }
}

