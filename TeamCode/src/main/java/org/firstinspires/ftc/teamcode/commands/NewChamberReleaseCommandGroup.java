package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Extension;

public class NewChamberReleaseCommandGroup extends SequentialCommandGroup {
    public NewChamberReleaseCommandGroup(RobotBase robotBase, Extension.ExtensionPosition clampPosition){
        addCommands(
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(clampPosition)),
                new WaitCommand(500),
                new InstantCommand(()->robotBase.clawSubsystem.openClaw()),
                new WaitCommand(750),
                new ExtensionHomeCommandGroup(robotBase.extensionSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem),
                new WaitUntilCommand(()->robotBase.extensionSubsystem.isExtensionHome()),
                new ShoulderHomeCommandGroup(robotBase.shoulderSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem)
        );
    }
}
