package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw extends SubsystemBase {

    Servo srvClaw;

    double dblOpenPosition = 0;
    double dblClosedPostion = 0;

    boolean bolClawOpen = true;

    public Claw(Servo clawServo) {
        srvClaw = clawServo;
        srvClaw.setPosition(dblOpenPosition);
    }

    public void OpenClaw() {
        srvClaw.setPosition(dblOpenPosition);
    }

    public void CloseClaw() {
        srvClaw.setPosition(dblClosedPostion);
    }

    public void ToggleClaw() {

        if(!bolClawOpen) {
            OpenClaw();
        } else {
            CloseClaw();
        }

        bolClawOpen = !bolClawOpen;
    }
}
