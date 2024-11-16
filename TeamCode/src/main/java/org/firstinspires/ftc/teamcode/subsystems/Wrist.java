package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

public class Wrist extends SubsystemBase {

    public enum WristPosition{
        HOME (0);
        public final double position;
        WristPosition(double m_position) {
            this.position = m_position;
        }
    }

    Servo srvWrist;

    public Wrist(Servo wristServo) {
    srvWrist = wristServo;
    srvWrist.setPosition(WristPosition.HOME.position);
    }

    public void goToPosition(WristPosition m_enmTargetPosition) {
srvWrist.setPosition(m_enmTargetPosition);

    }
}
