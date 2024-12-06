package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class PickupElbowWristCommandGroup extends SequentialCommandGroup {
    public PickupElbowWristCommandGroup(Wrist wrist, Elbow elbow){
        addCommands(
                new InstantCommand(()-> elbow.goToPosition(Elbow.ElbowPosition.PICKUP)),
                new InstantCommand(()-> wrist.goToPosition(Wrist.WristPosition.PICKUP))
        );
    }
}
