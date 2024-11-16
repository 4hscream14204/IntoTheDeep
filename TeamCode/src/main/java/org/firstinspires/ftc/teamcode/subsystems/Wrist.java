package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

public class Wrist extends SubsystemBase {

    public enum WristPosition{
        HOME (0),
        PICKUP (0),
        DROPOFF (0);
        public final double value;
        WristPosition(double m_position) {
            this.value = m_position;
        }
    }

    private Servo srvWrist;

    public WristPosition enmWristPosition;

    public Wrist(Servo wristServo) {
    srvWrist = wristServo;
    srvWrist.setPosition(WristPosition.HOME.value);
    }

    public void goToPosition(WristPosition enmTargetPosition) {
        srvWrist.setPosition(enmTargetPosition.value);
        enmWristPosition = enmTargetPosition;

    }

    public isAtPostition(WristPosition enmTargetPosition) {

    }
}
