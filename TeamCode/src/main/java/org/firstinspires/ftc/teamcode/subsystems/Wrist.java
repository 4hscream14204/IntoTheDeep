package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Wrist {

    private Servo srvWrist;

    public Wrist (Servo conWristServo){
        srvWrist = conWristServo;
    }
    public void wristStartPos(double position){
        srvWrist.setPosition(position);
    }
    public void wristPickupPos(double position){
        srvWrist.setPosition(position);
    }
    public void wristTransferPos(double position){
        srvWrist.setPosition(position);
    }
}
