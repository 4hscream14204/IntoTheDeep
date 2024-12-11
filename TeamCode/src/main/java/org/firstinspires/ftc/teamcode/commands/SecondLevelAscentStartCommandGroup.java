package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.SpecimenGrabber;

public class SecondLevelAscentStartCommandGroup extends SequentialCommandGroup {
    public SecondLevelAscentStartCommandGroup(Lift lift, SpecimenGrabber specimenGrabber) {
        addCommands(
                new InstantCommand(()-> lift.goToPosition(Lift.LiftPosition.SECONDACENTSTART)),
                new InstantCommand(specimenGrabber::grabberClosed)
        );
    }
}
