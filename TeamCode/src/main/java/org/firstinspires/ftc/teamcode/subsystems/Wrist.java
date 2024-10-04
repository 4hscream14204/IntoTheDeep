package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

public class Wrist extends SubsystemBase {

    private Servo srvWrist;
    private double home = 0.776;
    private double pickup = 1;
    private double transfer = .7016;

    public Wrist (Servo conWristServo){
        srvWrist = conWristServo;
        wristHomePos();
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
