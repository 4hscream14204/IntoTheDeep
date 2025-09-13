package org.firstinspires.ftc.teamcode.Opmode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.RobotBase;

@TeleOp(name = "Color Sensor TeleOP")
public class ColorSensorTeleOP extends OpMode {

    RobotBase robotBase;

    @Override
    public void init () {

    }

    public void loop() {
        telemetry.addData("ColorHue", robotBase.colorSensorSubsystem.getHueValues());
    }
}