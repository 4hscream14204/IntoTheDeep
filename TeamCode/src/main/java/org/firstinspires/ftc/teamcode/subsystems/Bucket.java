package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.robotcore.hardware.Servo;

public class Bucket {

    private Servo srvBucket;

    boolean bucketUp;

    public Bucket (Servo bucketServo) {
        srvBucket = bucketServo;
    }

    public void toggleBucket() {

        if (!bucketUp) {

            srvBucket.setPosition(1.0);

        } else {

            srvBucket.setPosition(0.722);

        }

    }






}