package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Extension;

public class SecondLevelAscentCommandGroupPartTwo extends SequentialCommandGroup {

    public SecondLevelAscentCommandGroupPartTwo (RobotBase robotBase) {

        addCommands(
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.SECONDLEVELASCENTPULL))
        );
    }
}
