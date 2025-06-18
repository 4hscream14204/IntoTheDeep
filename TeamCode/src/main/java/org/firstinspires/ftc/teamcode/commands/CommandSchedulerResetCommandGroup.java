package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.Robot;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;

public class CommandSchedulerResetCommandGroup extends SequentialCommandGroup {
    public CommandSchedulerResetCommandGroup(RobotBase robotBase){
        addCommands(
                new InstantCommand(()->robotBase.shoulderSubsystem.setPower(0)),
                new InstantCommand(()->robotBase.extensionSubsystem.setPower(0))
                /*new WaitCommand(500),
                new InstantCommand(()-> CommandScheduler.getInstance().reset()),
                new InstantCommand(()->robotBase.shoulderSubsystem.stopInPlace()),
                new InstantCommand(()->robotBase.extensionSubsystem.stopInPlace())*/
        );
    }
}
