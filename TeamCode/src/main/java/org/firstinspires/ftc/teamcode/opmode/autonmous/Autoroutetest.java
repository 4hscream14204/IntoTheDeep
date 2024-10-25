package org.firstinspires.ftc.teamcode.opmode.autonmous;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Autoroutetest extends OpMode {


 private Trajectory blueRight;
    SparkFunOTOS drive;
    @Override
     public void init (){
     drive = new ("sensor_otos")
     Trajectory blueRight = drive (new Pose2d(-23, 63, Math.toRadians(-90.00)))
             .splineToConstantHeading(new Vector2d(-12, 32), Math.toRadians(-90.00))
             .setReversed(true)
             .splineToConstantHeading(new Vector2d(-36, 34), Math.toRadians(-90.00))
             .setReversed(false)
             .splineToConstantHeading(new Vector2d(-36, 13), Math.toRadians(-90.00))
             .splineToConstantHeading(new Vector2d(-47, 14), Math.toRadians(90.00))
             .setReversed(true)
             .splineToConstantHeading(new Vector2d(-47, 31), Math.toRadians(90.00))
             .splineToConstantHeading(new Vector2d(-47, 57), Math.toRadians(90.00))
                .build();

    }

    @Override
    public void start() {

    }

    @Override
    public void loop (){

    }
}





