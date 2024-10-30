package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.SpecimenGrabber;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class PickupSpecimenCommandGroup extends SequentialCommandGroup {
    public PickupSpecimenCommandGroup(Lift lift, SpecimenGrabber specimenGrabber, Wrist wrist){
        addCommands(
                new InstantCommand(wrist::wristPickupPos),
                new InstantCommand(()->lift.goToPosition(Lift.LiftPosition.PICKUP)),
                new InstantCommand(specimenGrabber::grabberOpen),
                new WaitCommand(1500),
                new InstantCommand(specimenGrabber::grabberClosed),
                new WaitCommand(2000),
                new InstantCommand(()->lift.goToPosition(Lift.LiftPosition.PICKUPLIFT)),
                new WaitCommand(2000),
                new LiftHome(lift)
        );
    }
}
