package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class Shoulder extends SubsystemBase {

    public  DcMotorEx dcShoulderMotor;
  //  public DcMotorEx dcShoulderMotorLeft;
  //  public DcMotorEx dcShoulderMotorRight;
    public DigitalChannel tsShoulderLimitSwitch;
    public double dblUpPower = 1;
    public double dblDownPower = -0.5;
    public boolean bolStoppedInPlace = true;
    public int intCurrentPos;

    public enum ShoulderPosition{
        HOME (0),
        HIGHCHAMBER (2193),
        HIGHCHAMBERCLAMP (1975),
        NEWHIGHCHAMBER(2093),//2750, 3396
        LOWCHAMBER (680),
        LOWCHAMBERCLAMP (0),
        NEWLOWCHAMBER(2093),
        MAXPOSITION (1190),
        LOWBASKET (2093),
        HIGHBASKET (2093),
        TOGGLE (2093),
        AUTOPARK (2093),
        SECONDLEVELASCENT (12093);
        public final int height;
        ShoulderPosition(int high){
            this.height = high;
        }
    }

    public  enum MaxAmps{
        MAXAMPLEFT (999),
        MAXAMPRIGHT(999),
        MAXAMP (999);
        public final double max;
        MaxAmps(double cap){
            this.max = cap;
        }
    }

    public Shoulder(DcMotorEx conShoulderMotor, DigitalChannel conShoulderLimitSwitch) {
        dcShoulderMotor = conShoulderMotor;
        tsShoulderLimitSwitch = conShoulderLimitSwitch;
        dcShoulderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcShoulderMotor.setTargetPosition(0);
        dcShoulderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);;
        dcShoulderMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        enmShoulderPosition = ShoulderPosition.HOME;

        setPower(0);
        conShoulderMotor.setDirection(DcMotorSimple.Direction.REVERSE);

    }


    public ShoulderPosition enmShoulderPosition;

    public void goToPosition(ShoulderPosition enmTargetPosition){
        if(dcShoulderMotor.getCurrentPosition() < enmTargetPosition.height){
            //dcShoulderMotorLeft
            dcShoulderMotor.setPower(dblUpPower);
          /*  dcShoulderMotorLeft.setPower(dblUpPower);
            dcShoulderMotorRight.setPower(dblUpPower);

           */
        }
        else if(dcShoulderMotor.getCurrentPosition() > enmTargetPosition.height){
            dcShoulderMotor.setPower(dblDownPower);
          /*  dcShoulderMotorLeft.setPower(dblDownPower);
            dcShoulderMotorRight.setPower(dblDownPower);

           */
        }
        else{
            stopInPlace();
            return;
        }
        enmShoulderPosition = enmTargetPosition;
        dcShoulderMotor.setTargetPosition(enmTargetPosition.height);
        dcShoulderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        /*dcShoulderMotorLeft.setTargetPosition(enmTargetPosition.height);
        dcShoulderMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcShoulderMotorRight.setTargetPosition(enmTargetPosition.height);
        dcShoulderMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

         */

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
            dcShoulderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            dcShoulderMotor.setPower(power);
          /*  dcShoulderMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            dcShoulderMotorLeft.setPower(power);
            dcShoulderMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            dcShoulderMotorRight.setPower(power);

           */


            bolStoppedInPlace = false;
        }

    }
/*
    public void goUp(double power){
        if(dcShoulderMotor.getCurrentPosition() > ShoulderPosition.MAXPOSITION.height){
            //dcShoulderMotorLeft
            stopInPlace();
        }
        else {
            dcShoulderMotor.setPower(power);
            dcShoulderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         /*   dcShoulderMotorLeft.setPower(power);
            dcShoulderMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            dcShoulderMotorRight.setPower(power);
            dcShoulderMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

          */
           // bolStoppedInPlace = false;
    /*    }
    }

    public void goDown(double power){
       if(tsShoulderLimitSwitch.getState()){
            reset();
        }
       dcShoulderMotor.setPower(power);
       dcShoulderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       /* dcShoulderMotorLeft.setPower(power);
        dcShoulderMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dcShoulderMotorRight.setPower(power);
        dcShoulderMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        bolStoppedInPlace = false;
    }
*/
    public boolean isAtPosition(ShoulderPosition targetPosition){
        if(Math.abs(dcShoulderMotor.getCurrentPosition() - targetPosition.height) <= 30){
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
            intCurrentPos = dcShoulderMotor.getCurrentPosition();
            dcShoulderMotor.setTargetPosition(dcShoulderMotor.getCurrentPosition());
            dcShoulderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            setPower(0.2);
    }

    public int shoulderGetPosition(){
        return dcShoulderMotor.getCurrentPosition();
    }
                                               //dcShoulderMotorLeft
    public boolean isShoulderHome(){
       return tsShoulderLimitSwitch.getState();
    }

    public void reset(){
        bolStoppedInPlace = false;
        dcShoulderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcShoulderMotor.setTargetPosition(0);
        dcShoulderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        setPower(0);
    }

    public double getPower(){
        return dcShoulderMotor.getPower();
    }

    public void setPower(double m_Power) {
        /*if (isStalling()) {
            stopInPlace();
        } else {*/
            dcShoulderMotor.setPower(m_Power);
           // dcShoulderMotorLeft.setPower(m_Power);
           // dcShoulderMotorRight.setPower(m_Power);
        //}
    }

    public  double getAMP() {
        return dcShoulderMotor.getCurrent(CurrentUnit.AMPS);
    }

    public boolean isStalling(){
        return (getAMP() > MaxAmps.MAXAMP.max);
    }

    public void stopIfStalling(){
        if (isStalling()) {
            stopInPlace();
        }
    }
}
