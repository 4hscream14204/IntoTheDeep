package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.RobotBase;

@TeleOp(name = ("OTOS test"))
public class OTOSPositionTest extends OpMode {

    public RobotBase robotBase;
    private Follower follower;
    private final Pose beginningPose = new Pose(0, 0, Math.toRadians(0));
    private SparkFunOTOS otos;

    public void init() {
        follower = new Follower(hardwareMap);
        follower.setStartingPose(beginningPose);
        robotBase = new RobotBase(hardwareMap);
        robotBase.otos.setPosition(new SparkFunOTOS.Pose2D(0, 0, Math.toRadians(-90)));

    }

    public void loop() {
        follower.update();
        //follower.setStartingPose(new Pose(follower.getPose().getX(), follower.getPose().getY(), follower.getPose().getHeading()));

        telemetry.addData("x", robotBase.otos.getPosition().x);
        telemetry.addData("y", robotBase.otos.getPosition().y);
        telemetry.addData("heading", robotBase.otos.getPosition().h);
    }
}
