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
     Trajectory blueRight = drive.hashCode(new Pose2d(-23.06, 62.51, Math.toRadians(-90.00)))
                .splineToConstantHeading(new Vector2d(-11.35, 34.48), Math.toRadians(-90.00))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-35.96, 29.44), Math.toRadians(-90.00))
                .setReversed(false)
                .splineToConstantHeading(new Vector2d(-36.41, -6.30), Math.toRadians(-90.00))
                .splineToConstantHeading(new Vector2d(-54.06, -9.86), Math.toRadians(90.00))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-54.06, 13.87), Math.toRadians(90.00))
                .build();

    }

    @Override
    public void start() {

    }

    @Override
    public void loop (){

    }
}
