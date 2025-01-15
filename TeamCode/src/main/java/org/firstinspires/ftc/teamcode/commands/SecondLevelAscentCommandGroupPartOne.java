package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;

public class SecondLevelAscentCommandGroupPartOne extends SequentialCommandGroup {

    public SecondLevelAscentCommandGroupPartOne(RobotBase robotBase) {

        addCommands(
                new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.SECONDLEVELASCENT)),
                new WaitUntilCommand(()->robotBase.shoulderSubsystem.isAtPosition(Shoulder.ShoulderPosition.SECONDLEVELASCENT)),
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.SECONDLEVELASCENT)),
                new InstantCommand(()->robotBase.clawSubsystem.closeClaw())
        );
    }
}
