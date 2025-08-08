package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

public class Elbow extends SubsystemBase {

    public enum ElbowPosition {
        HOME (0.36111),
        INIT (0.32),
        PICKUP (0.9666666666666667),
        MAX (1),
        MIN (0),
        AUTO (0.5),
        PRESUBPICKUP (0.6777),
        DROPOFF (0.777),
        COLORSENSOREJECT (0.76388),
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