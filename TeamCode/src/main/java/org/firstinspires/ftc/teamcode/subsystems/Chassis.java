package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Chassis extends SubsystemBase {
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;
    double dblFrontLeftPower = 0;
    double dblFrontRightPower = 0;
    double dblBackLeftPower = 0;
    double dblBackRightPower = 0;
    double dblDenominator;
    double leftStickX;
    double leftStickY;
    double rotationPower;

    public Chassis(DcMotor m_frontLeftMotor, DcMotor m_frontRightMotor, DcMotor m_backLeftMotor, DcMotor m_backRightMotor){
        frontLeftMotor = m_frontLeftMotor;
        frontRightMotor = m_frontRightMotor;
        backLeftMotor = m_backLeftMotor;
        backRightMotor = m_backRightMotor;
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior){
        frontLeftMotor.setZeroPowerBehavior(zeroPowerBehavior);
        frontRightMotor.setZeroPowerBehavior(zeroPowerBehavior);
        backLeftMotor.setZeroPowerBehavior(zeroPowerBehavior);
        backRightMotor.setZeroPowerBehavior(zeroPowerBehavior);
    }

    public void setMode(DcMotor.RunMode mode){
        frontLeftMotor.setMode(mode);
        frontRightMotor.setMode(mode);
        backLeftMotor.setMode(mode);
        backRightMotor.setMode(mode);
    }

    public void drive(double m_leftStickX, double m_leftStickY, double m_rightStickX){
        leftStickX = (m_leftStickY * Math.abs(m_leftStickY) * -1);
        leftStickY = (m_leftStickX * Math.abs(m_leftStickX) * -1);
        rotationPower = m_rightStickX * Math.abs(m_rightStickX);



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

    public void testWheels(double m_leftStickX, double m_leftStickY, double m_rightStickX, boolean m_front, boolean m_left) {
        leftStickX = (m_leftStickY * Math.abs(m_leftStickY) * -1);
        leftStickY = m_leftStickX * Math.abs(m_leftStickX);
        rotationPower = m_rightStickX * Math.abs(m_rightStickX);



        dblDenominator = Math.max(Math.abs(leftStickX) + Math.abs(leftStickX) + Math.abs(rotationPower), 1);
        if (m_front) {
            if (m_left) {
                dblFrontLeftPower = (leftStickY + leftStickX + rotationPower) / dblDenominator;
            } else {
                dblFrontRightPower = (leftStickY - leftStickX - rotationPower) / dblDenominator;
            }

        } else {

            if (m_left) {
                dblBackLeftPower = (leftStickY - leftStickX + rotationPower) / dblDenominator;
            } else {
                dblBackRightPower = (leftStickY + leftStickX - rotationPower) / dblDenominator;
            }
        }

        frontLeftMotor.setPower(dblFrontLeftPower);
        frontRightMotor.setPower(dblFrontRightPower);
        backLeftMotor.setPower(dblBackLeftPower);
        backRightMotor.setPower(dblBackRightPower);
    }
}
