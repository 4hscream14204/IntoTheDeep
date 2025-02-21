package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.teamcode.base.RobotBase;

public class GyroResetCommandGroup extends SequentialCommandGroup {
    public GyroResetCommandGroup(RobotBase robotBase){
        addCommands(
                new InstantCommand(()->robotBase.chassisSubsystem.setTargetDegrees(0)),
                new WaitCommand(250),
                new InstantCommand(()->robotBase.chassisSubsystem.disablePIDUse())
        );
    }
}
