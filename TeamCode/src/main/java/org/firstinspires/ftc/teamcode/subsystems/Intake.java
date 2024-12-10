package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake extends SubsystemBase {

    public enum GatePosition{
        OPEN (0.2777),
        ClOSED (0.0555);
        public final double value;
        GatePosition(double m_position) {
            this.value = m_position;
        }
    }

    public Intake.GatePosition enmGatePosition;
    public Servo intakeServoLeft;
    public Servo intakeServoRight;
    public Servo intakeServoGate;
    public NormalizedColorSensor intakeColorSensor;

    public Intake(Servo m_intakeLeft, Servo m_intakeRight, Servo m_intakeGate/*NormalizedColorSensor m_intakesensor*/ ) {
            intakeServoLeft = m_intakeLeft;
            intakeServoRight = m_intakeRight;
            intakeServoGate = m_intakeGate;
            //intakeColorSensor = m_intakesensor;
    }

    public void intakeSpeed (double speed){
        if(speed > 0.5){
            intakeServoLeft.setPosition(1 - speed);
            intakeServoRight.setPosition(speed);
        }
        else if (speed < 0.5){
            intakeServoLeft.setPosition(speed + 1);
            intakeServoRight.setPosition(speed - 1);
        }
        else{
            intakeServoLeft.setPosition(0.5);
            intakeServoRight.setPosition(0.5);
        }
    }

    public NormalizedRGBA checkSampleColor(){
        return(intakeColorSensor.getNormalizedColors());
    }

    public void intakeOuttake(){
        intakeServoLeft.setPosition(0);
        intakeServoRight.setPosition(1);
    }

    public void gateGoToPosition(Intake.GatePosition enmTargetPosition) {
        intakeServoGate.setPosition(enmTargetPosition.value);
    }

    public boolean isAtPosition(Intake.GatePosition enmCheckPosition) {
        if (enmGatePosition == enmCheckPosition){
            return true;
        } else {
            return false;
        }
    }

    public void intakeStop(){
        intakeServoLeft.setPosition(0.5);
        intakeServoRight.setPosition(0.5);
    }

    public double getPosition() {
        return intakeServoGate.getPosition();
    }
}
