package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class ShoulderToggleIfHomeCommandGroup extends SequentialCommandGroup {

    public ShoulderToggleIfHomeCommandGroup(Shoulder shoulder, Elbow elbow, Wrist wrist){
            addCommands(
                    new InstantCommand(()-> shoulder.goToPosition(Shoulder.ShoulderPosition.TOGGLE)),
                    new InstantCommand(()->elbow.goToPosition(Elbow.ElbowPosition.PICKUP)),
                    new InstantCommand(()-> wrist.goToPosition(Wrist.WristPosition.PICKUP))
            );

        }
    }
