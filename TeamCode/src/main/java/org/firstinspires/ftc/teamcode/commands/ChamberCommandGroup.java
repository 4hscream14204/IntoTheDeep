package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

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
                    new WaitUntilCommand(()->robotBase.extensionSubsystem.isAtPosition(clampPosition)),
                    new InstantCommand(()->robotBase.clawSubsystem.openClaw()),
                    new WaitCommand(250),
                    new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP)),
                    new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP)),
                    new ParallelCommandGroup(new ShoulderHomeCommandGroup(robotBase.shoulderSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem),
                            new ExtensionHomeCommandGroup(robotBase.extensionSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem))/*,
                    new WaitCommand(1000),
                    new InstantCommand(()->robotBase.chassisSubsystem.setTargetDegrees(0)),
                    new WaitCommand(1000),
                    new InstantCommand(()->robotBase.chassisSubsystem.disablePIDUse())*/
            );
        }
        else{
            addCommands(
                    new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP)),
                    new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(chamberPosition)),
                    new WaitUntilCommand(()->robotBase.shoulderSubsystem.isAtPosition(chamberPosition)),
                    new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(extensionChamberPosition))
            );
        }
    }
}
