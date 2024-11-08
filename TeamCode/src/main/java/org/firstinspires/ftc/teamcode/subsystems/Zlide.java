package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

public class Zlide extends SubsystemBase {

    public double zlideStartPosition = 1;
    public double zlideExtendedPosition = 0;
    public double zlideBucketPosition = 0.526;

    private Servo srvZlide;

    public Zlide (Servo zlideServo) {

        srvZlide = zlideServo;
        zlideStartPosition();
    }

    public void zlideStartPosition() {

        srvZlide.setPosition(zlideStartPosition);

    }

    public void zlideExtendPosition() {

        srvZlide.setPosition(zlideExtendedPosition);

    }

    public void zlideBucketPosition() {

        srvZlide.setPosition(zlideBucketPosition);

    }

    public double GetPostion() {
        return(srvZlide.getPosition());
    }




}