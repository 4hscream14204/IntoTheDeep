package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

public class Sweeper extends SubsystemBase {

    public enum SweeperPosition {
        OUT(0),
        HOME(1);
        public final double position;
        SweeperPosition(double value) {
            this.position = value;
        }
    }

    Servo srvSweeper;

    boolean sweeperOpen = true;
    double servoRange;

    public Sweeper(Servo sweeperServo) {
        srvSweeper = sweeperServo;
        servoRange = SweeperPosition.OUT.position - SweeperPosition.HOME.position;
    }




    public void goToPosition (SweeperPosition m_targetPosition) {
        srvSweeper.setPosition(m_targetPosition.position);
        if (m_targetPosition == SweeperPosition.HOME) {
            sweeperOpen = false;
        } else {
            sweeperOpen = true;
        }
    }

    public void ToggleSweeper() {
        if (sweeperOpen) {
            goToPosition(SweeperPosition.HOME);
        } else {
            goToPosition(SweeperPosition.OUT);
        }
    }

    public void setPosition(double m_targetPosition) {
        srvSweeper.setPosition(m_targetPosition * servoRange + SweeperPosition.HOME.position);
    }
}
