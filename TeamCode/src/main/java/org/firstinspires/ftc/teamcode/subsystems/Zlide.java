package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Zlide {

    double startPosition = 0;
    double extendedPosition = 0;
    double bucketPosition = 0;

    private Servo srvZlide;

    public Zlide (Servo zlideServo) {

        srvZlide = zlideServo;

    }

    public void zlidePosition1() {

        srvZlide.setPosition(startPosition);

    }

    public void zlidePosition2() {

        srvZlide.setPosition(extendedPosition);

    }

    public void zlidePosition3() {

        srvZlide.setPosition(bucketPosition);

    }

}
