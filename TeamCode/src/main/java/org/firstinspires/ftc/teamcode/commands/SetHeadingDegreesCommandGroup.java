package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;

public class SetHeadingDegreesCommandGroup extends SequentialCommandGroup {
    public SetHeadingDegreesCommandGroup(RobotBase robotBase, double targetHeadingDegrees){
        if(Math.abs(Math.toDegrees(robotBase.otos.getPosition().h) - targetHeadingDegrees) >= 170){
            addCommands(
                    new InstantCommand(()->robotBase.chassisSubsystem.setTargetDegrees(Math.toDegrees(robotBase.otos.getPosition().h) - 90)),
                    new WaitCommand(100),
                    new InstantCommand(()->robotBase.chassisSubsystem.setTargetDegrees(targetHeadingDegrees)),
                    new WaitCommand(1000),
                    new InstantCommand(()->robotBase.chassisSubsystem.disablePIDUse())
            );
        }
        else{
            addCommands(
                    new InstantCommand(() -> robotBase.chassisSubsystem.setTargetDegrees(targetHeadingDegrees)),
                    new WaitCommand(1000),
                    new InstantCommand(() -> robotBase.chassisSubsystem.disablePIDUse())
            );
        }
    }
}
