package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Wrist {

    public enum WristPosition {
        FOLDEDIN (0.886111),
        FOLDEDOUT (0.5);
        public final double value;
        WristPosition(double m_position) {
            this.value = m_position;
        }
    }

    public Servo wristServo;

    public WristPosition enmWristPosition;

    public Wrist(Servo m_wristServo) {
        wristServo = m_wristServo;
        enmWristPosition = WristPosition.FOLDEDIN;
    }

    public void goToPosition(WristPosition enmTargetPosition) {
        wristServo.setPosition(enmTargetPosition.value);
        enmWristPosition = enmTargetPosition;

    }

    public boolean isAtPosition(WristPosition enmCheckPosition) {
        if(enmCheckPosition == enmWristPosition){
            return true;
        }
        return false;
    }

    public double getPosition() {
        return wristServo.getPosition();
    }
}
