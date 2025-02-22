package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.SpecimenGrabber;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class HighChamberScoreClampCommandGroup extends SequentialCommandGroup {
    public HighChamberScoreClampCommandGroup(Lift lift, SpecimenGrabber specimenGrabber, Wrist wrist){
        addCommands(
                new WaitUntilCommand(lift::isAtHighChamber),
                new InstantCommand(()->lift.goToPosition(Lift.LiftPosition.HIGHCHAMBERCLAMP)),
                new WaitCommand(500),
                new InstantCommand(specimenGrabber::grabberOpen),
                new WaitCommand(1000),
                new LiftHome(lift, wrist)
        );
    }
}
