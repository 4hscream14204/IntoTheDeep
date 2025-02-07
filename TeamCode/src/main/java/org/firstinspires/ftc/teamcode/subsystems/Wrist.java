package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

public class Wrist extends SubsystemBase {

    public enum WristPosition{
        HOME (1/*0.6555*/),
        PICKUP(0.29277/*0.25*/),
        PRESUBPICKUP(0.3055),
        BUCKETDROPOFF (0.09722/*0.36,0.4166,0.291666*/),
        AUTOINIT (0.25),
        HUMANPLAYERDROPOFF(0.777);
        public final double value;
        WristPosition(double m_position) {
            this.value = m_position;
        }
    }

    private Servo srvWrist;

    public WristPosition enmWristPosition;

    public Wrist(Servo wristServo) {
    srvWrist = wristServo;
   // srvWrist.setPosition(WristPosition.HOME.value);
    enmWristPosition = WristPosition.HOME;
    }

    public void goToPosition(WristPosition enmTargetPosition) {
        srvWrist.setPosition(enmTargetPosition.value);
        enmWristPosition = enmTargetPosition;

    }

    public boolean isAtPosition(WristPosition enmCheckPosition) {
        if(enmCheckPosition == enmWristPosition){
            return true;
        }
            return false;
    }

    public double getPosition() {
        return srvWrist.getPosition();
    }
}
