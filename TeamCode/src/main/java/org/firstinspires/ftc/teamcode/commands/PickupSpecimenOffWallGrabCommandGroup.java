package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class PickupSpecimenOffWallGrabCommandGroup extends SequentialCommandGroup {
    public PickupSpecimenOffWallGrabCommandGroup(Extension extension, Shoulder shoulder, Claw claw, Elbow elbow, Wrist wrist){
        addCommands(
                new InstantCommand(claw::closeClaw),
                new InstantCommand(()->shoulder.goToPosition(Shoulder.ShoulderPosition.HIGHCHAMBER))
        );
    }
}
