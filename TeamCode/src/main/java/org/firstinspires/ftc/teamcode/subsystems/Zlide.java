package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Zlide {

    double startPosition = 1;
    double extendedPosition = 0;
    double bucketPosition = 0.526;

    private Servo srvZlide;

    public Zlide (Servo zlideServo) {

        srvZlide = zlideServo;
        zlideStartPosition();
    }

    public void zlideStartPosition() {

        srvZlide.setPosition(startPosition);

    }

    public void zlideExtendPosition() {

        srvZlide.setPosition(extendedPosition);

    }

    public void zlideBucketPosition() {

        srvZlide.setPosition(bucketPosition);

    }



}
