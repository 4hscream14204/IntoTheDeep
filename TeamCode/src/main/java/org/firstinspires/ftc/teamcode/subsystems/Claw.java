package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw extends SubsystemBase {

    Servo srvClaw;

    double dblOpenPosition = 99;

    public Claw (Servo clawServo) {

        srvClaw = clawServo;
        srvClaw.setPosition(dblOpenPosition);

    }

    public OpenClaw ()
}
