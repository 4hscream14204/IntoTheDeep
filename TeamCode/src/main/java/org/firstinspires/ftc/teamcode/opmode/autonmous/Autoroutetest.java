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
        Pose2d beginPose = new Pose2d(20, 61, Math.toRadians(0));
        drive = new SparkFunOTOSDrive (hardwareMap, beginPose);
        blueRight = drive.actionBuilder(beginPose)
                .setTangent(Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(18, 38), Math.toRadians(-90.00), new TranslationalVelConstraint(15))
                .splineToConstantHeading(new Vector2d(18, 33), Math.toRadians(-90.00), new TranslationalVelConstraint(15))
                //.setTangent(Math.toRadians(90))
                // .setReversed(true)
               // .splineToConstantHeading(new Vector2d(-55, 60), Math.toRadians(180))
               // .splineToConstantHeading(new Vector2d(-60, 60), Math.toRadians(180.00), new TranslationalVelConstraint(15))
                //.setReversed(false)
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





