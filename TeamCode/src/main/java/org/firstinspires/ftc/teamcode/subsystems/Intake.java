package org.firstinspires.ftc.teamcode.subsystems;

import android.graphics.Color;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake extends SubsystemBase {

    final float[] hsvValues = new float[3];
    float gain = 3;


    public enum GatePosition{
        OPEN (0.343888),
        ClOSED (0);
        public final double value;
        GatePosition(double m_position) {
            this.value = m_position;
        }
    }

    public enum Colors{
        REDHIGH (330),
        REDLOW (15),
        BLUEHIGH (270),
        BLUELOW (200),
        YELLOWHIGH(65),
        YELLOWLOW(30);
        public final double value;
        Colors(double m_colorAmounts){this.value = m_colorAmounts;}
    }

    public enum ColorList{
        RED,
        BLUE,
        YELLOW;
    }

    public enum DisplayColors{
        RED(.2783),
        YELLOW(.3244),
        GREEN(.4944),
        BLUE(.59),
        PURPLE(.7183),
        WHITE(1),
        OFF(0);
        public final double value;
        DisplayColors(double m_color){this.value = m_color;}
    }

    public Intake.GatePosition enmGatePosition;
    public Servo intakeServoLeft;
    public Servo intakeServoRight;
    public Servo intakeServoGate;
    public NormalizedColorSensor intakeColorSensor;
    public Servo intakeLED;
    public Intake.DisplayColors enmColorHue;

    public double dblLastColorRead;
    public double currentTime;
    public double lastReadValue;

    public Intake(Servo m_intakeLeft, Servo m_intakeRight, Servo m_intakeGate, NormalizedColorSensor m_intakesensor, Servo m_IntakeLED) {
        intakeServoLeft = m_intakeLeft;
        intakeServoRight = m_intakeRight;
        intakeServoGate = m_intakeGate;
        intakeColorSensor = m_intakesensor;
        intakeLED = m_IntakeLED;
        intakeColorSensor.setGain(gain);
        //intakeServoGate.setPosition(GatePosition.ClOSED.value);
    }

    public void getTime(double time){
        currentTime = time;
    }

    public double getHueValues() {
        if (currentTime - dblLastColorRead > 100) {
            NormalizedRGBA colors = intakeColorSensor.getNormalizedColors();
            Color.colorToHSV(colors.toColor(), hsvValues);
            dblLastColorRead = currentTime;
            lastReadValue = hsvValues[0];
            return hsvValues[0];
        }
        return lastReadValue;
    }

    public boolean isColor(Intake.ColorList m_targetColor) {
        if (m_targetColor == ColorList.BLUE) {
            if (getHueValues() > Colors.BLUELOW.value && getHueValues() < Colors.BLUEHIGH.value) {
                return true;
            }
            return false;
        } else if (m_targetColor == ColorList.YELLOW) {
            if (getHueValues() > Colors.YELLOWLOW.value && getHueValues() < Colors.YELLOWHIGH.value) {
                return true;
            } else {
                return false;
            }
        } else if (m_targetColor == ColorList.RED) {
            if (getHueValues() < Colors.REDLOW.value && getHueValues() > Colors.REDHIGH.value) {
                return true;
            } else {
                return false;
            }
        }
        return false;

    }

    public void displaySampleColor() {
        if (getHueValues() > Colors.BLUELOW.value && getHueValues() < Colors.BLUEHIGH.value) {
            setColor(DisplayColors.BLUE);
        } else if (getHueValues() > Colors.YELLOWLOW.value && getHueValues() < Colors.YELLOWHIGH.value) {
            setColor(DisplayColors.YELLOW);
        } else if (getHueValues() < Colors.REDLOW.value && getHueValues() > Colors.REDHIGH.value) {
            setColor(DisplayColors.RED);
        } else {
            setColor(DisplayColors.OFF);
        }
    }

    public void setColor(Intake.DisplayColors enmTargetColor) {
        if (enmTargetColor != enmColorHue) {
            intakeLED.setPosition(enmTargetColor.value);
            enmColorHue = enmTargetColor;
        }
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
