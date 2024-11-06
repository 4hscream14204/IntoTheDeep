package org.firstinspires.ftc.teamcode.opmode.autonmous;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.roadrunner.SparkFunOTOSDrive;
@Autonomous
public class Autoroutetest extends OpMode {


    private Action blueRight;
    SparkFunOTOSDrive drive;
    @Override
    public void init (){
        Pose2d beginPose = new Pose2d(-14, 61, Math.toRadians(180));
        drive = new SparkFunOTOSDrive (hardwareMap, beginPose);
        blueRight = drive.actionBuilder(beginPose)
                .setTangent(Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(-13, 45), Math.toRadians(90.00), new TranslationalVelConstraint(20))
                .splineToConstantHeading(new Vector2d(-13, 33), Math.toRadians(90.00), new TranslationalVelConstraint(20))
                .waitSeconds(5)
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-40, 59), Math.toRadians(0.00))
                .splineToConstantHeading(new Vector2d(-52, 59), Math.toRadians(0.00), new TranslationalVelConstraint(20))
                .build();
    }

    @Override
    public void start() {
        Actions.runBlocking(blueRight);
    }

    @Override
    public void loop (){

    }
}





