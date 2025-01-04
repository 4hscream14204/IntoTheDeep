package com.example.meepmeeptesting;

public class MeepMeepMicah {TrajectorySequence trajectory0 = drive.trajectorySequenceBuilder(new Pose2d(8.82, 70.81, Math.toRadians(90.00)))
        .splineToConstantHeading(new Vector2d(7.79, 34.48), Math.toRadians(264.37))
        .splineTo(new Vector2d(23.95, 46.64), Math.toRadians(-18.12))
        .splineToConstantHeading(new Vector2d(40.12, 33.44), Math.toRadians(-12.77))
        .splineToLinearHeading(new Pose2d(58.06, 59.25, Math.toRadians(120.00)), Math.toRadians(58.68))
        .splineToLinearHeading(new Pose2d(49.16, 29.29, Math.toRadians(180.00)), Math.toRadians(-86.71))
        .splineToLinearHeading(new Pose2d(62.95, 55.54, Math.toRadians(-180.00)), Math.toRadians(54.73))
        .splineTo(new Vector2d(67.55, 56.43), Math.toRadians(254.77))
        .splineToLinearHeading(new Pose2d(59.25, 31.22, Math.toRadians(180.00)), Math.toRadians(254.96))
        .splineTo(new Vector2d(63.99, 61.17), Math.toRadians(90.00))
        .splineTo(new Vector2d(48.27, 18.91), Math.toRadians(230.56))
        .splineTo(new Vector2d(24.40, 12.23), Math.toRadians(179.50))
        .build();

}
