package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

public class Elbow extends SubsystemBase {

    public enum ElbowPosition {
        HOME (0.2311),
        INIT (0.2311),
        PICKUP (1),
        MAX (1),
        MIN (0),
        AUTO (0.18333),
        PRESUBPICKUP (0.7238),
        DROPOFF (0.6288),
        AUTODROPOFF (0.8),
        COLORSENSOREJECT (0.80444),
        GIVETOHUMAN (0.6777);
        public final double value;
        ElbowPosition(double m_pos){
            this.value = m_pos;
        }
    }

    public Servo elbowServo;

    public ElbowPosition enmElbowPosition;

    public Elbow(Servo conElbowServo) {
        elbowServo = conElbowServo;
        //  goToPosition(ElbowPosition.INIT);
        enmElbowPosition = ElbowPosition.HOME;
    }

    public boolean isAtPosition(ElbowPosition elbowTarget){
        if(elbowTarget == enmElbowPosition){
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