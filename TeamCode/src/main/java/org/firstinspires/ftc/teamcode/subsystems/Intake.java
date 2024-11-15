package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake extends SubsystemBase {

    public Servo intakeServoLeft;
    public Servo intakeServoRight;
    public Servo intakeServoGate;
    public NormalizedColorSensor intakeColorSensor;

    public Intake(Servo m_intakeLeft, Servo m_intakeRight, Servo m_intakeGate, NormalizedColorSensor m_intakesensor ) {
    }
}
