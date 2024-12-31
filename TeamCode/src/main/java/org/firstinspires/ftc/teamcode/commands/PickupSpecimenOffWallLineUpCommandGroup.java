package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class PickupSpecimenOffWallLineUpCommandGroup extends SequentialCommandGroup {
    public PickupSpecimenOffWallLineUpCommandGroup(Extension extension, Shoulder shoulder, Elbow elbow, Wrist wrist, Claw claw){
        addCommands(
                new ExtensionHomeCommandGroup(extension, elbow),
                new InstantCommand(()->wrist.goToPosition(Wrist.WristPosition.PICKUP)),
                new InstantCommand(()->elbow.goToPosition(Elbow.ElbowPosition.PICKUP)),
                new InstantCommand(claw::openClaw),
                new InstantCommand(()->shoulder.goToPosition(Shoulder.ShoulderPosition.TOGGLE))
        );
    }
}
