package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Claw {

    Servo srvClaw;

    public enum ClawPositions {
        OPEN(0.5),
        CLOSED(0.83);

        public final double rotation;
        ClawPositions(double rot) {
            this.rotation = rot;
        }
    }

    public Claw(Servo clawServo) {
        srvClaw = clawServo;
    }

    boolean bolClawOpen = true;

    public void togglePosition() {
        if (bolClawOpen) {
            closeClaw();
        } else if(!bolClawOpen){
            openClaw();
        } else {
            //what
        }
    }

    public void closeClaw() {
        srvClaw.setPosition(ClawPositions.CLOSED.rotation);
        bolClawOpen = false;
    }

    public void openClaw() {
        srvClaw.setPosition(ClawPositions.OPEN.rotation);
        bolClawOpen = true;
    }
}
