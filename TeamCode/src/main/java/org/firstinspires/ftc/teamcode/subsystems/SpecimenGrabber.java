package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

public class SpecimenGrabber extends SubsystemBase {

    double dubOpenPosition = 0.10888;
    double dubClosedPosition = 0.630555;

    Servo srvSpecimenGrabber;

    boolean bolOpen = true;

    public SpecimenGrabber (Servo specimenGrabberServo) {

        srvSpecimenGrabber = specimenGrabberServo;
        srvSpecimenGrabber.setPosition(dubOpenPosition);
    }

    public void toggleGrabber () {

        if (bolOpen) {

            srvSpecimenGrabber.setPosition(dubClosedPosition);
            bolOpen = false;
        } else {

            srvSpecimenGrabber.setPosition(dubOpenPosition);
            bolOpen = true;
        }
    }
}
