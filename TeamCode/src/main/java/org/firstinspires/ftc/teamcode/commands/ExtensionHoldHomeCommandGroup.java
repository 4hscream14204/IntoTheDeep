package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.Robot;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.base.RobotBase;

public class ExtensionHoldHomeCommandGroup extends SequentialCommandGroup {
    public ExtensionHoldHomeCommandGroup(RobotBase robotBase, double shoulderPower) {
        addCommands(
                //robotBase.extensionSubsystem.extend(shoulderPower);
        );
    }
}
