package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;

public class SetHeadingDegreesCommandGroup extends SequentialCommandGroup {
    public SetHeadingDegreesCommandGroup(RobotBase robotBase, double targetHeadingDegrees){
        addCommands(
               new InstantCommand(()->robotBase.chassisSubsystem.setTargetDegrees(targetHeadingDegrees)),
                new WaitCommand(1000),
                new InstantCommand(()->robotBase.chassisSubsystem.disablePIDUse())
        );
    }
}
