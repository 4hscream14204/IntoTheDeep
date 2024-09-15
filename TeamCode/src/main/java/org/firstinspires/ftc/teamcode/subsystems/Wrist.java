package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Wrist {

    private Servo srvWrist;
    private double home = 0;
    private double pickup = .5;
    private double transfer = .25;

    public Wrist (Servo conWristServo){
        srvWrist = conWristServo;
    }
    public void wristHomePos(){
        srvWrist.setPosition(home);
    }
    public void wristPickupPos(){
        srvWrist.setPosition(pickup);
    }
    public void wristTransferPos(){
        srvWrist.setPosition(transfer);
    }
}
