package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Sweeper;

public class ToggleSweeperCommandGroup extends SequentialCommandGroup {
    public ToggleSweeperCommandGroup(RobotBase robotBase) {
        addCommands(
                new InstantCommand(()->robotBase.sweeperSubsystem.goToPosition(Sweeper.SweeperPosition.CLOSED)),
                new WaitCommand(750),
                new InstantCommand(()->robotBase.sweeperSubsystem.goToPosition(Sweeper.SweeperPosition.OPEN))
        );
    }
}
