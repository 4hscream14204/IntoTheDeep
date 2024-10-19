package org.firstinspires.ftc.teamcode.subsystems;


import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

public class Bucket extends SubsystemBase {

    double bucketUpPosition = 0.47222;
    double bucketDownPosition = 0.79333;

     Servo srvBucket;

    boolean bucketUp = false;

    public Bucket (Servo bucketServo) {

        srvBucket = bucketServo;
        srvBucket.setPosition(bucketDownPosition);

    }

    public void toggleBucket() {

        if (bucketUp) {
            bucketDownPosition();

        } else {

            bucketUpPosition();

        }

    }
        public void bucketDownPosition(){
            srvBucket.setPosition(bucketDownPosition);
            bucketUp = false;
    }
        public void bucketUpPosition(){
            srvBucket.setPosition(bucketUpPosition);
            bucketUp = true;
        }





}