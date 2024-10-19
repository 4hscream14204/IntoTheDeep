package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

public class SpecimenGrabber extends SubsystemBase {

    double dubOpenPosition = 0.2022;
    double dubClosedPosition = 0.49111;

    Servo srvSpecimenGrabber;

    boolean bolOpen = true;

    public SpecimenGrabber (Servo specimenGrabberServo) {

        srvSpecimenGrabber = specimenGrabberServo;
        srvSpecimenGrabber.setPosition(dubOpenPosition);
    }

    public void toggleGrabber () {

        if (bolOpen) {

            srvSpecimenGrabber.setPosition(dubClosedPosition);
        } else {

            srvSpecimenGrabber.setPosition(dubOpenPosition);
        }
    }
}
