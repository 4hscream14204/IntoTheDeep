package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

public class Elbow extends SubsystemBase {
    public Servo elbowServo;
    public double homeElbowServoPosition = 0;
    public double pickupElbowServoPosition = 0;

    public Elbow(Servo conElbowServo) {
        elbowServo = conElbowServo;
        elbowServo.setPosition(homeElbowServoPosition);
    }

    public void elbowHomePosition(){
        elbowServo.setPosition(homeElbowServoPosition);
    }

    public void elbowPickupPosition(){
        elbowServo.setPosition(pickupElbowServoPosition);
    }
}