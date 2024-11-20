package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

public class Elbow extends SubsystemBase {

    public enum ElbowPosition {
        HOME (0);
        public final int value;
        ElbowPosition(int m_pos){
            this.value = m_pos;
        }
    }

    public Servo elbowServo;

    public ElbowPosition enmElbowPosition;

    public Elbow(Servo conElbowServo) {
        elbowServo = conElbowServo;
        goToPosition(ElbowPosition.HOME);
    }

    public boolean isAtPosition(ElbowPosition elbowTarget){
        if(Math.abs(elbowServo.getPosition() - elbowTarget.value) <= 0.05){
            return true;
        }
        return false;
    }

    public void goToPosition(ElbowPosition m_position){
        enmElbowPosition = m_position;
        elbowServo.setPosition(m_position.value);
    }

    public double getPosition(){
        return elbowServo.getPosition();
    }
}