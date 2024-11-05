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

    public void intakeStop(){
        srvIntake.setPosition(0.5);
    }

    public void intakeOuttake(){
        srvIntake.setPosition(0);
    }
}