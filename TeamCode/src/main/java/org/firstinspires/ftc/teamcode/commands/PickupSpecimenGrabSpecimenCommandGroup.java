package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.SpecimenGrabber;

public class PickupSpecimenGrabSpecimenCommandGroup extends SequentialCommandGroup {
    public PickupSpecimenGrabSpecimenCommandGroup(SpecimenGrabber specimenGrabber, Lift lift){
        addCommands(
                new InstantCommand(specimenGrabber::grabberClosed),
                new WaitCommand(2000),
                new InstantCommand(()->lift.goToPosition(Lift.LiftPosition.PICKUPLIFT)),
                new WaitCommand(2500),
                new LiftHome(lift)
        );
    }
}
