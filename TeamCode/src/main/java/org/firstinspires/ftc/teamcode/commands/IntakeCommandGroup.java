package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.firstinspires.ftc.teamcode.subsystems.Zlide;

public class IntakeCommandGroup extends SequentialCommandGroup {
    public IntakeCommandGroup(Zlide zlide, Wrist wrist){
        addCommands(
                new InstantCommand(zlide::zlideExtendPosition),
                new InstantCommand(wrist::wristPickupPos)
        );
    }

}