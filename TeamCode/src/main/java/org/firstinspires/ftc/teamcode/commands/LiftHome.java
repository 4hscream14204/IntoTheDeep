package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.subsystems.Lift;

public class LiftHome extends SequentialCommandGroup {
    public LiftHome(Lift lift) {
        addCommands(
                new InstantCommand(()->lift.goToPosition(Lift.LiftPosition.HOME)),
                new WaitUntilCommand(lift::getSwitchState),
                new InstantCommand(lift::reset)
        );
    }
}