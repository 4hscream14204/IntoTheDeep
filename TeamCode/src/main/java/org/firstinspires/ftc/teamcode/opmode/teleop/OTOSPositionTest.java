package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.RobotBase;

@TeleOp(name = ("OTOS test"))
public class OTOSPositionTest extends OpMode {

    public RobotBase robotBase;
    private Follower follower;
    private final Pose beginningPose = new Pose(0, 0, Math.toRadians(0));

    public void init() {
        follower = new Follower(hardwareMap);
        follower.setStartingPose(beginningPose);

    }

    public void loop() {

        follower.update();

        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
    }
}
