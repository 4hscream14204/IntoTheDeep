package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class HangSpecimenAutoCommandGroupPartOne extends SequentialCommandGroup {

    public HangSpecimenAutoCommandGroupPartOne(RobotBase robotBase) {

        addCommands(
                new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.NEWHIGHCHAMBER)),
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.NEWHIGHCHAMBER)),
                new WaitCommand(250),
                new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP)),
                new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP))
        );
    }
}
