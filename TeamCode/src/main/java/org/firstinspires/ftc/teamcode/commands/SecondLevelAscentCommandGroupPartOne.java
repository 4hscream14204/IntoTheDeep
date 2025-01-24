package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;

public class SecondLevelAscentCommandGroupPartOne extends SequentialCommandGroup {

    public SecondLevelAscentCommandGroupPartOne(RobotBase robotBase) {

        addCommands(
                new InstantCommand(()->robotBase.clawSubsystem.closeClaw()),
                new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP)),
                new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.TOGGLE)),
                new WaitUntilCommand(()->robotBase.shoulderSubsystem.isAtPosition(Shoulder.ShoulderPosition.TOGGLE)),
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.SECONDLEVELASCENT)),
                new WaitUntilCommand(()->robotBase.extensionSubsystem.isAtPosition(Extension.ExtensionPosition.SECONDLEVELASCENT)),
                new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.SECONDLEVELASCENT))
        );
    }
}
