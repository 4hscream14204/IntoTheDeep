package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;

public class ChamberDropOffReleaseAndHomeCommandGroup extends SequentialCommandGroup {
    public ChamberDropOffReleaseAndHomeCommandGroup(RobotBase robotBase){
        addCommands(
                new InstantCommand(()->robotBase.clawSubsystem.openClaw()),
                new WaitCommand(250),
                new ExtensionHomeCommandGroup(robotBase.extensionSubsystem, robotBase.elbowSubsystem),
                new WaitUntilCommand(()->robotBase.extensionSubsystem.isExtensionHome()),
                new ShoulderHomeCommandGroup(robotBase.shoulderSubsystem)
        );
    }
}
