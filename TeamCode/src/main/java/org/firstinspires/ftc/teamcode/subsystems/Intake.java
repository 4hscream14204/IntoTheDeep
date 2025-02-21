package org.firstinspires.ftc.teamcode.subsystems;

import android.graphics.Color;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.ITDCrabEnums;

public class Intake extends SubsystemBase {

    final float[] hsvValues = new float[3];

    public enum GatePosition{
        OPEN (0.343888),
        ClOSED (0);
        public final double value;
        GatePosition(double m_position) {
            this.value = m_position;
        }
    }

    public enum Colors{
        RED (0),
        BLUE (0),
        GREEN(0);
        public final double value;
        Colors(double m_colorAmounts){this.value = m_colorAmounts;}
    }

    public Intake.GatePosition enmGatePosition;
    public Servo intakeServoLeft;
    public Servo intakeServoRight;
    public Servo intakeServoGate;
    public NormalizedColorSensor intakeColorSensor;

    public Intake(Servo m_intakeLeft, Servo m_intakeRight, Servo m_intakeGate, NormalizedColorSensor m_intakesensor) {
        intakeServoLeft = m_intakeLeft;
        intakeServoRight = m_intakeRight;
        intakeServoGate = m_intakeGate;
        intakeColorSensor = m_intakesensor;
        //intakeServoGate.setPosition(GatePosition.ClOSED.value);
    }

    NormalizedRGBA colors = intakeColorSensor.getNormalizedColors();

    public double getHueValues() {
        Color.colorToHSV(colors.toColor(), hsvValues);
        return hsvValues[0];
    }

    public boolean isColor(Intake.Colors m_targetColor) {
        if (m_targetColor.value == getHueValues()) {
            return true;
        }

        return false;
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

    /*public int checkSampleColorRed(){
        return intakeColorSensor.red();
    }

    public int checkSampleColorBlue(){
        return intakeColorSensor.blue();
    }

    public int checkSampleColorGreen(){
        return intakeColorSensor.green();
    }*/

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

    /*public boolean isRedSample(){
        if(Math.abs(Red.RED.value - intakeColorSensor.red()) <= 10){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isBlueSample(){
        if(Math.abs(Blue.BLUE.value - intakeColorSensor.blue()) <= 10){
            return true;
        }
        else{
            return false;
        }
    }*/

    /*public boolean isMyColor(){
        if (Math.abs(checkSampleColor().red - Red.RED.value) <= dblColorMarginOfError) {
            if (DataStorage.alliance.equals(ITDCrabEnums.EnmAlliance.RED)) {
                return true;
            } else {
                return false;
            }
        } else if (Math.abs(checkSampleColor().blue - Blue.BLUE.value) <= dblColorMarginOfError) {
            if (DataStorage.alliance.equals(ITDCrabEnums.EnmAlliance.BLUE)) {
                return true;
            }
            else{
                return false;
            }
        }
        else if(Math.abs(checkSampleColor().green - Yellow.GREEN.value) <= dblColorMarginOfError){
            return true;
        }
        else{
            return false;
        }*/
}
