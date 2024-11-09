package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.firstinspires.ftc.teamcode.subsystems.Zlide;

public class LiftHome extends SequentialCommandGroup {
    public LiftHome(Lift lift, Wrist wrist) {
        if(wrist.getPosition() == wrist.wristTransfer){
            new InstantCommand(wrist::wristPickupPos);
        };
        addCommands(
                new InstantCommand(()->lift.goDown(lift.downPower)),
                new WaitUntilCommand(lift::getSwitchState),
                new InstantCommand(lift::reset)
        );
    }
}