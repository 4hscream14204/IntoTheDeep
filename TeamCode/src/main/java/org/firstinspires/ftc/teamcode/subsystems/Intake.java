package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake extends SubsystemBase {

    Servo srvIntake;

    public Intake (Servo conIntakeServo){
        srvIntake = conIntakeServo;
    }

    public void intakeSpeed(double speed){
        srvIntake.setPosition(speed);
    }
}
