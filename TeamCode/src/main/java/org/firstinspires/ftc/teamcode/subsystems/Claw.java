package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw extends SubsystemBase {

    Servo srvClaw;

    public enum clawPosition {
        OPEN(1),
        CLOSED(0.9);
        public final double value;
        clawPosition(double m_pos){
            this.value = m_pos;
        }
    }

    boolean bolClawOpen = true;

    public Claw(Servo clawServo) {
        srvClaw = clawServo;
        srvClaw.setPosition(clawPosition.CLOSED.value);
    }

    public void openClaw() {
        srvClaw.setPosition(clawPosition.OPEN.value);
        bolClawOpen = true;
    }

    public void closeClaw() {
        srvClaw.setPosition(clawPosition.CLOSED.value);
        bolClawOpen = false;
    }

    public void toggleClaw() {

        if(!bolClawOpen) {
            openClaw();
        } else {
            closeClaw();
        }
    }

    public boolean isBolClawOpen() {
        return bolClawOpen;
    }
}
