package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw extends SubsystemBase {

    Servo srvClaw;

    public enum clawPosition {
        OPEN(0),
        CLOSED(0);
        public final int value;
        clawPosition(int m_pos){
            this.value = m_pos;
        }
    }

    boolean bolClawOpen = true;

    public Claw(Servo clawServo) {
        srvClaw = clawServo;
        srvClaw.setPosition(clawPosition.OPEN.value);
    }

    public void OpenClaw() {
        srvClaw.setPosition(clawPosition.OPEN.value);
        bolClawOpen = true;
    }

    public void CloseClaw() {
        srvClaw.setPosition(clawPosition.CLOSED.value);
        bolClawOpen = false;
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
