package org.firstinspires.ftc.teamcode.opmode.auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.roadrunner.SparkFunOTOSDrive;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.roadrunner.SparkFunOTOSDrive;

public class BlueRight {
    @Autonomous
    public class blueRight extends OpMode {


        private Action blueRight;
        SparkFunOTOSDrive drive;
        @Override
        public void init (){
            Pose2d beginPose = new Pose2d(-23, 63, Math.toRadians(-90.00));
            drive = new SparkFunOTOSDrive (hardwareMap, beginPose);
            blueRight = drive.actionBuilder(beginPose)
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
            Actions.runBlocking(blueRight);
        }

        @Override
        public void loop (){

        }
    }

}
