package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;

public class Heading extends SubsystemBase {
    PIDController headingControl = new PIDController(5, 0, 0);
}
