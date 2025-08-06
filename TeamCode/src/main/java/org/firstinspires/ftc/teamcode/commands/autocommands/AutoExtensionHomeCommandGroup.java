package org.firstinspires.ftc.teamcode.commands.autocommands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class AutoExtensionHomeCommandGroup extends SequentialCommandGroup {

    public AutoExtensionHomeCommandGroup(Extension extension, Elbow elbow, Wrist wrist){
        addCommands(
                new InstantCommand(()-> extension.extend(extension.dblDownPower)),
                new WaitUntilCommand(extension::isExtensionHome),
                new InstantCommand(extension::reset),
                new InstantCommand(()-> elbow.goToPosition(Elbow.ElbowPosition.PICKUP)),
                new InstantCommand(()->wrist.goToPosition(Wrist.WristPosition.PICKUP))
        );
    }
}
