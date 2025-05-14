package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake extends SubsystemBase {

    public CRServo intakeServo;
    public double dblCurrentSpeed;

    public Intake(CRServo m_intakeServo) {
        intakeServo = m_intakeServo;
    }

    public void intakeSpeed (double speed){
        dblCurrentSpeed = speed;

        intakeServo.setPower(speed);
    }

    public double getSpeed() {
        return dblCurrentSpeed;
    }
}
