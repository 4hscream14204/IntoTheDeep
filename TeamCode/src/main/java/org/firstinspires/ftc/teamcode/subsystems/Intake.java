package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake extends SubsystemBase {

    public Servo intakeServoLeft;
    public Servo intakeServoRight;
    public Servo intakeServoGate;
    public NormalizedColorSensor intakeColorSensor;

    public Intake(Servo m_intakeLeft, Servo m_intakeRight, Servo m_intakeGate, NormalizedColorSensor m_intakesensor ) {
            intakeServoLeft = m_intakeLeft;
            intakeServoRight = m_intakeRight;
            intakeServoGate = m_intakeGate;
            intakeColorSensor = m_intakesensor;
    }

    public void intakeSpeed (double speed){
        intakeServoLeft.setPosition(speed);
        intakeServoRight.setPosition(speed);
    }

    public NormalizedRGBA checkSampleColor(){
        return(intakeColorSensor.getNormalizedColors());
    }

    public void intakeOuttake(){
        intakeServoLeft.setPosition(0);
        intakeServoRight.setPosition(0);
    }
}
