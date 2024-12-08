package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;

public class ExtensionHomeCommandGroup  extends SequentialCommandGroup {
    public ExtensionHomeCommandGroup(Extension extension, Elbow elbow){
        addCommands(
                new InstantCommand(()-> extension.extendBack(extension.dblDownPower)),
                new WaitUntilCommand(extension::isExtensionHome),
                new InstantCommand(extension::reset),
                new WaitCommand(500),
        new InstantCommand(()-> elbow.goToPosition(Elbow.ElbowPosition.PRESUBPICKUP))
        );
    }
}
