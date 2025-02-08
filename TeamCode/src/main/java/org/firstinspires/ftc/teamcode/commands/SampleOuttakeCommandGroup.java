package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;

public class SampleOuttakeCommandGroup extends SequentialCommandGroup {
    public SampleOuttakeCommandGroup(RobotBase robotBase){
        addCommands(
                new InstantCommand(()->robotBase.intakeSubsystem.intakeOuttake()),
                new WaitCommand(250),
                new InstantCommand(()->robotBase.intakeSubsystem.intakeStop())
        );
    }
}
