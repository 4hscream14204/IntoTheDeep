package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;

public class HangSpecimenAutoCommandGroup extends SequentialCommandGroup {

    public HangSpecimenAutoCommandGroup(RobotBase robotBase) {

        addCommands(
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.NEWHIGHCHAMBERCLAMP)),
                new WaitCommand(250),
                new InstantCommand(()->robotBase.clawSubsystem.openClaw()),
                new WaitCommand(500),
                new InstantCommand(()-> robotBase.extensionSubsystem.extend(robotBase.extensionSubsystem.dblDownPower)),
                new WaitUntilCommand(robotBase.extensionSubsystem::isExtensionHome),
                new InstantCommand(robotBase.extensionSubsystem::reset),
                // new ExtensionHomeCommandGroup(robotBase.extensionSubsystem, robotBase.elbowSubsystem),
                new WaitUntilCommand(()->robotBase.extensionSubsystem.isExtensionHome()),
                new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.TOGGLE))
                );
    }
}
