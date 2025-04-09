package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Lift extends SubsystemBase {
    public DcMotor liftMotor;
    public double upPower = 0.5;
    public double downPower = -0.5;
    public Servo bucketServo;

    public enum LiftPosition {
        HOME (0),
        HIGHDROPOFF (400);
        public final int height;
        LiftPosition(int high){
            this.height = high;
        }
    }

    public enum BucketPosition {
        HOME (0.85),
        DROPOFF(0.15);
        public final double height;
        BucketPosition(double high){
            this.height = high;
        }
    }

    public double dblUpPower = -1;
    public double dblDownPower = 1;
    public boolean bolStopped = true;
    public int intCurrentPos;
    public int intMaxPosition;

    public LiftPosition enmExtensionPosition;

    public Lift(DcMotor m_LiftMotor, Servo m_bucketSrv) {
        liftMotor = m_LiftMotor;
        bucketServo = m_bucketSrv;
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setTargetPosition(0);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        enmExtensionPosition = LiftPosition.HOME;
        bucketServo.setPosition(0.85);
    }

    public void setPower(double power){
        liftMotor.setPower(power);
    }

    public void setTargetPosition(int position){
        liftMotor.setTargetPosition(position);
    }

    public void setMode(DcMotor.RunMode mode){
        liftMotor.setMode(mode);
    }

    public void extend(double power) {
        if(extensionGetPosition() < intMaxPosition){
            stopInPlace();
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


    public void goToPosition(LiftPosition enmTargetPosition) {
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
        else{
            setMode(DcMotor.RunMode.RUN_TO_POSITION);
            intCurrentPos = extensionGetPosition();
            setTargetPosition(intCurrentPos);
            setPower(-0.1);
        }
        bolStopped = true;
    }

    public boolean isAtPosition(LiftPosition targetPosition){
        if(Math.abs(extensionGetPosition() - targetPosition.height) <= 50){
            return true;
        }
        return false;
    }

    public int extensionGetPosition(){
        return liftMotor.getCurrentPosition();
    }

    public double getPower(){
        return liftMotor.getPower();
    }

    public int getTargetPosition(){
        return liftMotor.getTargetPosition();
    }
}
