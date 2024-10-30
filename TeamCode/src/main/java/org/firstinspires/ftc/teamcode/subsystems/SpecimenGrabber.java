package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

public class SpecimenGrabber extends SubsystemBase {

    double dubOpenPosition = 0.31166667;
    double dubClosedPosition = 0.72777;

    Servo srvSpecimenGrabber;

    boolean bolOpen = true;

    public SpecimenGrabber (Servo specimenGrabberServo) {

        srvSpecimenGrabber = specimenGrabberServo;
        srvSpecimenGrabber.setPosition(dubOpenPosition);
    }

    public void toggleGrabber () {

        if (bolOpen) {

            grabberClosed();
        } else {

            grabberOpen();
        }
    }

    public void grabberOpen(){
        srvSpecimenGrabber.setPosition(dubOpenPosition);
        bolOpen = true;
    }

    public void grabberClosed(){
        srvSpecimenGrabber.setPosition(dubClosedPosition);
        bolOpen = false;
    }
}
