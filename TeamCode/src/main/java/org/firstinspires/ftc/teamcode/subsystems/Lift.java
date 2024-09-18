package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Lift {

    public DcMotor liftMotor;
    public double speed = 0;
    public int home = 0;
    public int highBasket = -810;
    public int highChamber = -600;
    public int pickup = -100;
    public double upPower= -0.5;
    public double downPower = 0.4;

    public Lift (DcMotor conLiftMotor){
        liftMotor = conLiftMotor;
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setTargetPosition(0);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor.setPower(0);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void goDown(double power){
        if(liftMotor.getCurrentPosition()>home-30){
            liftMotor.setPower(0);
        }
        else {
            liftMotor.setPower(power * 0.1);
        }
    }

    public void goUp(double power){
        if(liftMotor.getCurrentPosition()<(highBasket)){
            liftMotor.setPower(0);
        }
        else{
            liftMotor.setPower(power * -0.7);
        }
    }

    public void stop () {
        liftMotor.setPower(0);
    }

    public void home (){
        liftMotor.setPower(downPower);
        liftMotor.setTargetPosition(home);
    }
    public void highBasket(){
        liftMotor.setTargetPosition(highBasket);
    }
    public void highChamber(){
        if(liftMotor.getCurrentPosition()<highChamber){
            liftMotor.setPower(downPower);
        }
        else if(liftMotor.getCurrentPosition()>highChamber){
            liftMotor.setPower(upPower);
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

    public int getPosition() {
        return liftMotor.getCurrentPosition();
    }
}
