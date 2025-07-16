package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.base.RobotBase;

@Autonomous(name = "OTOSCoordinatesTool")
public class OTOSCoordinatesTool extends OpMode {
    public Pose2d startPose;
    public RobotBase robotBase;
    @Override
    public void init(){
        startPose = new Pose2d(14, 61, Math.toRadians(0));
        robotBase = new RobotBase(hardwareMap);
        robotBase.drive.pose = startPose;
    }
    @Override
    public void loop(){
        telemetry.addData("X", robotBase.drive.otos.getPosition().x);
        telemetry.addData("Y", robotBase.drive.otos.getPosition().y);
        telemetry.addData("Heading", robotBase.drive.otos.getPosition().h);
    }
}
