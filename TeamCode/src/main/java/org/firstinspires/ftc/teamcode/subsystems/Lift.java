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
            liftMotor.setPower(power * 0.2);
            stopped=false;
        }
    }

    public void goUp(double power){
        if(liftMotor.getCurrentPosition()<(highBasket)){
            stop();
        }
        else{
            liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            liftMotor.setPower(power * -0.8);
            stopped=false;
        }
    }

    public void stop () {

        if(stopped==true) {
            return;
        }
        stopped=true;
        liftMotor.setTargetPosition(liftMotor.getCurrentPosition());
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setPower(-0.3);
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
