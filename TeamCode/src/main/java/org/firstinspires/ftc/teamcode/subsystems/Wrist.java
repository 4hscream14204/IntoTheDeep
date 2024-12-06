package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

public class Wrist extends SubsystemBase {

    private Servo srvWrist;
    public double wristHome = 0.776;
    public double wristPickup = 1;
    public double wristTransfer = .75;

    public Wrist (Servo conWristServo){
        srvWrist = conWristServo;
        wristHomePos();
    }
    public void wristHomePos(){
        srvWrist.setPosition(wristHome);
    }
    public void wristPickupPos(){
        srvWrist.setPosition(wristPickup);
    }
    public void wristTransferPos(){
        srvWrist.setPosition(wristTransfer);
    }

    public double getPosition(){
        return srvWrist.getPosition();
    }
}