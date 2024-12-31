package org.firstinspires.ftc.teamcode.opmode.auto;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class BlueRightx4 extends OpMode {

    @Override
    public void init() {

    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {


        .splineToConstantHeading(new Vector2d(-5.56, 30.62), Math.toRadians(300.00))
                .splineToSplineHeading(new Pose2d(-33.15, 34.78, Math.toRadians(120.00)), Math.toRadians(200.00))
                .splineToLinearHeading(new Pose2d(-35.96, 40.26, Math.toRadians(50.00)), Math.toRadians(120.00))
                .splineToLinearHeading(new Pose2d(-41.45, 34.33, Math.toRadians(120.00)), Math.toRadians(200.00))
                .splineToLinearHeading(new Pose2d(-45.90, 41.01, Math.toRadians(50.00)), Math.toRadians(120.00))
                .splineToLinearHeading(new Pose2d(-50.20, 34.04, Math.toRadians(120.00)), Math.toRadians(200.00))
                .splineToLinearHeading(new Pose2d(-54.20, 39.52, Math.toRadians(20.00)), Math.toRadians(120.00))
                .splineToSplineHeading(new Pose2d(-49.01, 55.84, Math.toRadians(180.00)), Math.toRadians(87.06))
                .splineToConstantHeading(new Vector2d(-5.86, 29.14), Math.toRadians(270.00))
                .splineToConstantHeading(new Vector2d(-49.16, 56.28), Math.toRadians(150.00))
                .splineToConstantHeading(new Vector2d(-2.60, 28.70), Math.toRadians(270.00))
                .splineToConstantHeading(new Vector2d(-49.31, 55.98), Math.toRadians(150.00))
                .splineToConstantHeading(new Vector2d(0.07, 28.99), Math.toRadians(270.00))
                .splineToConstantHeading(new Vector2d(-62.81, 62.66), Math.toRadians(90.00))

                .build();

    }

    @Override
    public void stop() {

    }
}
