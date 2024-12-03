package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

public class Wrist extends SubsystemBase {

    public enum WristPosition{
        HOME (0.6555),
        PICKUP (0.5666),
        BUCKETDROPOFF (0.291666);
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

    public boolean isAtPosition(WristPosition enmCheckPosition) {
        if(enmWristPosition == enmCheckPosition){
            return true;
        } else {
            return false;
        }
    }

    public double getPosition() {
        return srvWrist.getPosition();
    }
}
