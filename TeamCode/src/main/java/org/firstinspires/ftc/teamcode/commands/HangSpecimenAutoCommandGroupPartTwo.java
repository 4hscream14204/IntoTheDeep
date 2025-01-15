package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;

public class HangSpecimenAutoCommandGroupPartTwo extends SequentialCommandGroup {

    public HangSpecimenAutoCommandGroupPartTwo (RobotBase robotBase) {

        addCommands(
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.NEWHIGHCHAMBERCLAMP)),
                new InstantCommand(()->robotBase.clawSubsystem.openClaw()),
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME)),
                new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.HOME))
        );
    }
}
