package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.roadrunner.PathBuilder;

public class GeneratedPath {

    public GeneratedPath() {
        PathBuilder builder = new PathBuilder();

        builder
                .addPath(
                        // Line 1
                        new BezierCurve(
                                new Point(8.862, 55.606, Point.CARTESIAN),
                                new Point(25.477, 55.606, Point.CARTESIAN),
                                new Point(24.812, 75.545, Point.CARTESIAN)
                        )
                )
                .setTangentHeadingInterpolation();
    }
}