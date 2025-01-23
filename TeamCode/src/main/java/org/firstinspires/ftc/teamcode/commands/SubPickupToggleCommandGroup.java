package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class SubPickupToggleCommandGroup extends SequentialCommandGroup {
    public SubPickupToggleCommandGroup(Wrist wrist, Elbow elbow, Intake intake, Shoulder shoulder){
        if(elbow.isAtPosition(Elbow.ElbowPosition.PRESUBPICKUP) && wrist.isAtPosition(Wrist.WristPosition.PRESUBPICKUP) && shoulder.isShoulderHome()) {
            addCommands(
                    new InstantCommand(() -> wrist.goToPosition(Wrist.WristPosition.PICKUP)),
                    new InstantCommand(() -> elbow.goToPosition(Elbow.ElbowPosition.PICKUP))
            );
        }
        else if(elbow.isAtPosition(Elbow.ElbowPosition.PICKUP) && wrist.isAtPosition(Wrist.WristPosition.PICKUP) && shoulder.isShoulderHome()){
            addCommands(
                    new InstantCommand(()-> wrist.goToPosition(Wrist.WristPosition.PRESUBPICKUP)),
                    new InstantCommand(()->elbow.goToPosition(Elbow.ElbowPosition.PRESUBPICKUP))
            );
        }
        else if(elbow.isAtPosition(Elbow.ElbowPosition.HOME) && wrist.isAtPosition(Wrist.WristPosition.HOME) && shoulder.isShoulderHome()){
            addCommands(
                    new InstantCommand(()->elbow.goToPosition(Elbow.ElbowPosition.PRESUBPICKUP)),
                    new InstantCommand(()->wrist.goToPosition(Wrist.WristPosition.PRESUBPICKUP))
            );
        }
        else if(elbow.isAtPosition(Elbow.ElbowPosition.PRESUBPICKUP) && wrist.isAtPosition(Wrist.WristPosition.HOME)){
            addCommands(
                    new InstantCommand(()->elbow.goToPosition(Elbow.ElbowPosition.PICKUP)),
                    new InstantCommand(()->wrist.goToPosition(Wrist.WristPosition.PICKUP))
            );
        }
        else{
            addCommands(
                    new InstantCommand(()->elbow.goToPosition(Elbow.ElbowPosition.PICKUP)),
                    new InstantCommand(()->wrist.goToPosition(Wrist.WristPosition.PICKUP))
            );
        }
    }
}
