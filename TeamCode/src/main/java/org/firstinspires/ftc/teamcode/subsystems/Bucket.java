package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.robotcore.hardware.Servo;

public class Bucket {

    double bucketUpPosition = 0;
    double bucketDownPosition = 0.67;

    private Servo srvBucket;

    boolean bucketUp;

    public Bucket (Servo bucketServo) {

        srvBucket = bucketServo;
        srvBucket.setPosition(bucketDownPosition);

    }

    public void toggleBucket() {

        if (bucketUp) {

            srvBucket.setPosition(bucketDownPosition);
            bucketUp = false;

        } else {

            srvBucket.setPosition(bucketUpPosition);
            bucketUp = true;

        }

    }






}