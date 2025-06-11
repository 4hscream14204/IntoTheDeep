package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.ITDCrabEnums;

public class TimerLED extends SubsystemBase {

    private Servo ledTimer;

    public TimerLED(Servo timerLED) {
        ledTimer = timerLED;
    }

    public enum Colors{
        RED(.2783),
        YELLOW(.3244),
        GREEN(.4944),
        BLUE(.59),
        PURPLE(.7183),
        WHITE(1),
        OFF(0);
        public final double value;
        Colors(double m_color){this.value = m_color;}
    }

    public double dblEstimatedCycleTime = 6000;
    public int intSpecimensToDeliver = 0;
    public double dblEstimatedHangTime = 5000;
    public double dblMarginOfError = 3000;
    public TimerLED.Colors enmColorHue;

    public void ledSuggestion(double dblTimerLength){
        double m_remainingTime = 120000 - dblTimerLength;
        if (DataStorage.strategy == ITDCrabEnums.Strategy.SPECIMENSTOCKPILE) {
            if (dblEstimatedCycleTime * intSpecimensToDeliver + dblEstimatedHangTime + dblMarginOfError < m_remainingTime) {
                setColor(Colors.GREEN);

            } else if (dblEstimatedHangTime + dblMarginOfError < m_remainingTime) {
                setColor(Colors.PURPLE);

            } else {
                setColor(Colors.WHITE);

            }
        } else {
            if (dblEstimatedHangTime + dblMarginOfError < m_remainingTime) {
                setColor(Colors.PURPLE);

            } else {
                setColor(Colors.WHITE);
            }
        }
    }

    public void setColor(Colors enmTargetColor) {
        if (enmTargetColor != enmColorHue) {
            ledTimer.setPosition(enmTargetColor.value);
            enmColorHue = enmTargetColor;
        }
    }

    public boolean endgameRumble(double dblTimerLength){
        double m_remainingTime = 120000 - dblTimerLength;
        if(m_remainingTime <= 30){
            return true;
        }
        else{
            return false;
        }
    }
}
