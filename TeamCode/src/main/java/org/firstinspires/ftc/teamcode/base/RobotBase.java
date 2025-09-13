package org.firstinspires.ftc.teamcode.base;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import org.firstinspires.ftc.teamcode.subsystems.ColorSensor;

public class RobotBase {

    public ColorSensor colorSensorSubsystem;

    public RobotBase(HardwareMap hwMap){
        colorSensorSubsystem = new ColorSensor(hwMap.get(NormalizedColorSensor.class, "color_sensor"));
    }
}
