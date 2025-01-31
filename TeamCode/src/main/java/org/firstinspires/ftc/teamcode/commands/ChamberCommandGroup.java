package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;

public class ChamberCommandGroup extends SequentialCommandGroup {
    public ChamberCommandGroup(RobotBase robotBase, Shoulder.ShoulderPosition chamberPosition, Extension.ExtensionPosition extensionChamberPosition, Extension.ExtensionPosition clampPosition){
        if(robotBase.shoulderSubsystem.isAtPosition(chamberPosition) && !robotBase.extensionSubsystem.isAtPosition(extensionChamberPosition)){
            addCommands(
                    new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP)),
                    new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(chamberPosition)),
                    new WaitUntilCommand(()->robotBase.shoulderSubsystem.isAtPosition(chamberPosition)),
                    new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(extensionChamberPosition))
            );
        }
        if(robotBase.shoulderSubsystem.isAtPosition(chamberPosition) && robotBase.extensionSubsystem.isAtPosition(extensionChamberPosition)){
            addCommands(
                    new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(clampPosition)),
                    new WaitCommand(500),
                    new InstantCommand(()->robotBase.clawSubsystem.openClaw()),
                    new WaitCommand(250),
                    new ExtensionHomeCommandGroup(robotBase.extensionSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem),
                    new WaitUntilCommand(()->robotBase.extensionSubsystem.isExtensionHome()),
                    new ShoulderHomeCommandGroup(robotBase.shoulderSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem)
            );
        }
    }
}
