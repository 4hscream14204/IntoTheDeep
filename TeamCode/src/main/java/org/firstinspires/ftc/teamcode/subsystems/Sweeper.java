package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

public class Sweeper extends SubsystemBase {

    Servo srvSweeper;

    boolean sweeperOpen = true;

    public Sweeper(Servo sweeperServo) {
        srvSweeper = sweeperServo;
    }


    public enum SweeperPosition {
        OPEN (0),
        CLOSED (0);
        public final double position;
        SweeperPosition(double value) {
            this.position = value;
        }
    }

    public void goToPosition (SweeperPosition m_targetPosition) {
        srvSweeper.setPosition(m_targetPosition.position);
        if (m_targetPosition == SweeperPosition.CLOSED) {
            sweeperOpen = false;
        } else {
            sweeperOpen = true;
        }
    }

    public void ToggleSweeper() {
        if (sweeperOpen) {
            goToPosition(SweeperPosition.CLOSED);
        } else {
            goToPosition(SweeperPosition.OPEN);
        }
    }
}
