package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Extension extends SubsystemBase {

    DcMotor extendMotor;

    double dblUpPower = 0;
    double dblDownPower = 0;
    int home = 0;

    public Extension(DcMotor extensionMotor) {
        extendMotor = extensionMotor;
        extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extendMotor.setTargetPosition(home);
        extendMotor.setPower(dblDownPower);
    }

    public void extendDown() {
        extendMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extendMotor.setPower(dblDownPower);
    }

    public void extendUp() {
        extendMotor.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
        extendMotor.setPower(dblUpPower);
    }
}
