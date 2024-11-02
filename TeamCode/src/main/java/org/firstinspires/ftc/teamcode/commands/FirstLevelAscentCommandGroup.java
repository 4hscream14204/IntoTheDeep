package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.SpecimenGrabber;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class FirstLevelAscentCommandGroup extends SequentialCommandGroup {
    public FirstLevelAscentCommandGroup(Lift lift, SpecimenGrabber specimenGrabber, Wrist wrist){
        addCommands(
                new InstantCommand(wrist::wristPickupPos),
                new InstantCommand(()->lift.goToPosition(Lift.LiftPosition.FIRSTLEVELASCENT)),
                new InstantCommand(specimenGrabber::grabberClosed)
        );
    }
}
