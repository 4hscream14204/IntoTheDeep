package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.SpecimenGrabber;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class HighChamberScoreCommandGroup extends SequentialCommandGroup {
    public HighChamberScoreCommandGroup(Lift lift, SpecimenGrabber specimenGrabber, Wrist wrist){
        addCommands(
                new InstantCommand(wrist::wristPickupPos),
                new InstantCommand(()->lift.goToPosition(Lift.LiftPosition.HIGHCHAMBERSTART)),
                new WaitUntilCommand(lift::isAtHighChamber),
                new WaitCommand(2000),
                new InstantCommand(()->lift.goToPosition(Lift.LiftPosition.HIGHCHAMBERCLAMP)),
                new WaitCommand(1500),
                new InstantCommand(specimenGrabber::toggleGrabber),
                new WaitCommand(1000),
                new InstantCommand(specimenGrabber::toggleGrabber)
        );
    }
}
