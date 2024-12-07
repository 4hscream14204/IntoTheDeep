package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.subsystems.Shoulder;

public class ShoulderHomeCommandGroup extends SequentialCommandGroup {
    public ShoulderHomeCommandGroup(Shoulder shoulder){
        addCommands(
                new InstantCommand(()-> shoulder.goDown(shoulder.dblDownPower)),
                new WaitUntilCommand(shoulder::isShoulderHome),
                new InstantCommand(shoulder::reset)
        );
    }
}
