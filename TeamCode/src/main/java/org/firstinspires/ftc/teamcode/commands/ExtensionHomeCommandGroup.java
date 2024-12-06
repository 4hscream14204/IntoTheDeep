package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.subsystems.Extension;

public class ExtensionHomeCommandGroup  extends SequentialCommandGroup {
    public ExtensionHomeCommandGroup(Extension extension){
        addCommands(
                new InstantCommand(()-> extension.extendBack(extension.dblDownPower)),
                new WaitUntilCommand(extension::isExtensionHome),
                new InstantCommand(extension::reset)
        );
    }
}
