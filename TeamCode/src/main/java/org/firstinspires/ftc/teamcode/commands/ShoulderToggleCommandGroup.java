package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class ShoulderToggleCommandGroup extends SequentialCommandGroup {

    public ShoulderToggleCommandGroup(Shoulder shoulder, Elbow elbow, Wrist wrist){
        if (shoulder.isShoulderHome()) {
            addCommands(
                    new InstantCommand(()-> shoulder.goToPosition(Shoulder.ShoulderPosition.TOGGLE)),
                    new InstantCommand(()->elbow.goToPosition(Elbow.ElbowPosition.PICKUP)),
                    new InstantCommand(()-> wrist.goToPosition(Wrist.WristPosition.PICKUP))
            );

        }else {
            addCommands(

            new InstantCommand(()-> shoulder.goToPosition(Shoulder.ShoulderPosition.HOME)),
                    new InstantCommand(()-> elbow.goToPosition(Elbow.ElbowPosition.HOME)),
                    new InstantCommand(()-> wrist.goToPosition(Wrist.WristPosition.HOME))
            );

        }
    }
}
