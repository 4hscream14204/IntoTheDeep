package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;

public class NewChamberLineUpCommandGroup extends SequentialCommandGroup {
    public NewChamberLineUpCommandGroup(RobotBase robotBase, Shoulder.ShoulderPosition chamberPosition, Extension.ExtensionPosition extensionChamberPosition){
        addCommands(
                new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP)),
                new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(chamberPosition)),
                new WaitUntilCommand(()->robotBase.shoulderSubsystem.isAtPosition(chamberPosition)),
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(extensionChamberPosition)),
                new WaitUntilCommand(()->robotBase.extensionSubsystem.isAtPosition(extensionChamberPosition))
        );
    }
}
