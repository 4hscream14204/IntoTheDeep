package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.firstinspires.ftc.teamcode.subsystems.Zlide;

public class ZlideStartPositionCommandGroup extends SequentialCommandGroup {
    public ZlideStartPositionCommandGroup(Zlide zlide, Wrist wrist){
        addCommands(
                new InstantCommand(wrist::wristPickupPos),
                new InstantCommand(zlide::zlideStartPosition)
        );
    }
}
