package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.firstinspires.ftc.teamcode.subsystems.Zlide;

public class LiftHome extends SequentialCommandGroup {
    public LiftHome(Lift lift, Wrist wrist) {
        if(wrist.getPosition() == wrist.wristTransfer){
            addCommands(
                    new InstantCommand(wrist::wristPickupPos),
                    new WaitCommand(500),
                    new InstantCommand(() -> lift.goDown(lift.downPower)),
                    new WaitUntilCommand(lift::getSwitchState),
                    new InstantCommand(lift::reset)
            );
        }
        else {
            addCommands(
                    new InstantCommand(() -> lift.goDown(lift.downPower)),
                    new WaitUntilCommand(lift::getSwitchState),
                    new InstantCommand(lift::reset)
            );
        }
        }
    }