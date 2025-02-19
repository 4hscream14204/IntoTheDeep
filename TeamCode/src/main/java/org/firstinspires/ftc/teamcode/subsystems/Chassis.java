package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.roadrunner.ftc.SparkFunOTOSCorrected;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.base.RobotBase;


public class Chassis extends SubsystemBase {

    PIDController headingControl = new PIDController(5, 0, 0);
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;
    double dblFrontLeftPower;
    double dblFrontRightPower;
    double dblBackLeftPower;
    double dblBackRightPower;
    boolean bolFieldCentric = true;
    double dblDenominator;
    boolean isInPIDControl;
    double leftStickX;
    double leftStickY;
    double rotationPower;
    double botHeading;
    double dblLastStickTime;
    double dblCurrentTime;
    double dblDelayTime = 200;
    double dblTargetHeading;
    double dblHeadingDeviation;
    double dblHeadingOutput = 0;
    ElapsedTime timer;
    SparkFunOTOSCorrected otos;


    public Chassis(DcMotor m_frontLeftMotor, DcMotor m_frontRightMotor, DcMotor m_backLeftMotor, DcMotor m_backRightMotor, ElapsedTime m_timer, SparkFunOTOSCorrected m_otos){
        frontLeftMotor = m_frontLeftMotor;
        frontRightMotor = m_frontRightMotor;
        backLeftMotor = m_backLeftMotor;
        backRightMotor = m_backRightMotor;
        timer = m_timer;
        otos = m_otos;
        headingControl.setSetPoint(0);
    }

    public void drive(double m_leftStickX, double m_leftStickY, double m_rightStickX){
        leftStickX = (m_leftStickX * Math.abs(m_leftStickY) * -1);
        leftStickY = m_leftStickX * Math.abs(m_leftStickX);
        rotationPower = m_rightStickX * Math.abs(m_rightStickX);
        botHeading = otos.getPosition().h;
        dblCurrentTime = timer.milliseconds();

        if(bolFieldCentric){
            double rotX = leftStickX * Math.cos(-botHeading) - leftStickY * Math.sin(-botHeading);
            double rotY = leftStickX * Math.sin(-botHeading) + leftStickY * Math.cos(-botHeading);

            if(rotationPower > 0.01) {
                    dblLastStickTime = dblCurrentTime;
                }
            else if((dblCurrentTime - dblLastStickTime) < dblDelayTime) {
                dblTargetHeading = dblCurrentTime;
            }
            if(isInPIDControl){
                    dblHeadingDeviation = botHeading - dblTargetHeading;
                    dblHeadingDeviation = AngleUnit.normalizeRadians(dblHeadingDeviation);
                    dblHeadingOutput = headingControl.calculate(dblHeadingDeviation);
                    rotationPower = dblHeadingOutput;
                }

            dblDenominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rotationPower), 1);
            dblFrontLeftPower = (rotY + rotX + rotationPower) / dblDenominator;
            dblBackLeftPower = (rotY - rotX + rotationPower) / dblDenominator;
            dblFrontRightPower = (rotY - rotX - rotationPower) / dblDenominator;
            dblBackRightPower = (rotY + rotX - rotationPower) / dblDenominator;
        }
        else{
                dblDenominator = Math.max(Math.abs(leftStickX) + Math.abs(leftStickX) + Math.abs(rotationPower), 1);
                dblFrontLeftPower = (leftStickY + leftStickX + rotationPower) / dblDenominator;
                dblBackLeftPower = (leftStickY - leftStickX + rotationPower) / dblDenominator;
                dblFrontRightPower = (leftStickY - leftStickX - rotationPower) / dblDenominator;
                dblBackRightPower = (leftStickY + leftStickX - rotationPower) / dblDenominator;
        }

    }

    public void enableFieldCentric(){
        bolFieldCentric = true;
    }

    public void disableFieldCentric(){
        bolFieldCentric = false;
    }

    public void toggleFieldCentric(){
        if(bolFieldCentric){
            bolFieldCentric = false;
        }
        else{
            bolFieldCentric = true;
        }
    }

    public void enablePIDUse(){
        isInPIDControl = true;
    }

    public void disablePIDUse(){
        isInPIDControl = false;
    }

    public void togglePIDUse(){
        if(isInPIDControl){
            isInPIDControl = false;
        }
        else{
            isInPIDControl = true;
        }
    }
}
