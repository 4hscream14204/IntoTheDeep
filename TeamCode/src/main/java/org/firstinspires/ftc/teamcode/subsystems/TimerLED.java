package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.opmode.teleop.CrabTeleOp;

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

    public double dblEstimatedCycleTime = 10;
    public int intSpecimensToDeliver = 0;
    public double dblEstimatedHangTime = 5;
    public double dblMarginOfError = 3;

    public void getSuggestion(double dblTimerLength){
        double m_remainingTime = 120 - dblTimerLength;
        if (dblEstimatedCycleTime * intSpecimensToDeliver + dblEstimatedHangTime + dblMarginOfError < m_remainingTime) {
            setColor(Colors.BLUE);
        } else if (dblEstimatedHangTime +dblMarginOfError < m_remainingTime) {
            setColor(Colors.PURPLE);
        } else {
            setColor(Colors.RED);
        }
    }

    public void setColor(Colors enmTargetColor) {
        ledTimer.setPosition(enmTargetColor.value);
    }
}
