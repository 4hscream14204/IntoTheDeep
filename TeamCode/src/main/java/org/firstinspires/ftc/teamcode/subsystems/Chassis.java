package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.SparkFunOTOSCorrected;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.localization.Pose;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;


public class Chassis extends SubsystemBase {

    PIDController headingControl = new PIDController(2, 0, 0.25);
    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor backLeftMotor;
    public DcMotor backRightMotor;
    double dblFrontLeftPower;
    double dblFrontRightPower;
    double dblBackLeftPower;
    double dblBackRightPower;
    public boolean bolFieldCentric = true;
    double dblDenominator;
    public boolean isInPIDControl = false;
    double leftStickX;
    double leftStickY;
    double rotationPower;
    double botHeading;
    double dblLastStickTime = 0;
    public double dblCurrentTime = 0;
    double dblDelayTime = 0;
    public double dblTargetHeading = 0;
    double dblHeadingDeviation;
    double dblHeadingOutput = 0;
    public ElapsedTime timer;
    //public SparkFunOTOSCorrected otos;
    //public SparkFunOTOS otos;
    //public SparkFunOTOS.Pose2D botPose;


    public Chassis(DcMotor m_frontLeftMotor, DcMotor m_frontRightMotor, DcMotor m_backLeftMotor, DcMotor m_backRightMotor) {
        frontLeftMotor = m_frontLeftMotor;
        frontRightMotor = m_frontRightMotor;
        backLeftMotor = m_backLeftMotor;
        backRightMotor = m_backRightMotor;
        //timer = m_timer;
        //otos = m_otos;
        //headingControl.setSetPoint(0);
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        //dblCurrentTime = timer.milliseconds();
        //dblLastStickTime = timer.milliseconds();
        isInPIDControl = false;
        //botPose = otos.getPosition();
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
        frontLeftMotor.setZeroPowerBehavior(zeroPowerBehavior);
        frontRightMotor.setZeroPowerBehavior(zeroPowerBehavior);
        backLeftMotor.setZeroPowerBehavior(zeroPowerBehavior);
        backRightMotor.setZeroPowerBehavior(zeroPowerBehavior);
    }

    public void setMode(DcMotor.RunMode mode) {
        frontLeftMotor.setMode(mode);
        frontRightMotor.setMode(mode);
        backLeftMotor.setMode(mode);
        backRightMotor.setMode(mode);
    }

    public void drive(double m_leftStickX, double m_leftStickY, double m_rightStickX) {
        leftStickX = (m_leftStickX * Math.abs(m_leftStickX) * -1);
        leftStickY = m_leftStickY * Math.abs(m_leftStickY);
        rotationPower = m_rightStickX * Math.abs(m_rightStickX);
        //botPose = otos.getPosition();
        //botHeading = botPose.h;
        //dblCurrentTime = timer.milliseconds();

            dblDenominator = Math.max(Math.abs(leftStickX) + Math.abs(leftStickX) + Math.abs(rotationPower), 1);
            dblFrontLeftPower = (leftStickY + leftStickX + rotationPower) / dblDenominator;
            dblBackLeftPower = (leftStickY - leftStickX + rotationPower) / dblDenominator;
            dblFrontRightPower = (leftStickY - leftStickX - rotationPower) / dblDenominator;
            dblBackRightPower = (leftStickY + leftStickX - rotationPower) / dblDenominator;

        frontLeftMotor.setPower(dblFrontLeftPower);
        frontRightMotor.setPower(dblFrontRightPower);
        backLeftMotor.setPower(dblBackLeftPower);
        backRightMotor.setPower(dblBackRightPower);
    }

    /*public void setTargetDegrees(double targetHeadingDegrees){
        isInPIDControl = true;
        dblTargetHeading = Math.toRadians(targetHeadingDegrees);
    }

    public void enableFieldCentric(){
        bolFieldCentric = true;
        headingControl.reset();
    }

    public void disableFieldCentric(){
        bolFieldCentric = false;
    }

    public void toggleFieldCentric(){
        if(bolFieldCentric){
            disableFieldCentric();
        }
        else{
            enableFieldCentric();
        }
    }

    public void enablePIDUse(double targetDegrees){
        isInPIDControl = true;
        headingControl.reset();
        setTargetDegrees(targetDegrees);
    }

    public void disablePIDUse(){
        isInPIDControl = false;
    }

    public void togglePIDUse(){
        if(isInPIDControl){
            disablePIDUse();
        }
        else{
            enablePIDUse(botHeading);
        }*/
    }
