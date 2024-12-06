package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.SpecimenGrabber;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class LowChamberCommandGroup extends SequentialCommandGroup {
    public LowChamberCommandGroup (Lift lift, SpecimenGrabber specimenGrabber, Wrist wrist){
        addCommands(
                new InstantCommand(wrist::wristPickupPos),
                new InstantCommand(()->lift.goToPosition(Lift.LiftPosition.LOWCHAMBERSTART)),
                new WaitUntilCommand(()->lift.isAtBasket(Lift.LiftPosition.LOWCHAMBERSTART)),
                new WaitCommand(2000),
                new LiftHome(lift, wrist),
                new WaitCommand(750),
                new InstantCommand(specimenGrabber::grabberOpen)
        );
    }
}
