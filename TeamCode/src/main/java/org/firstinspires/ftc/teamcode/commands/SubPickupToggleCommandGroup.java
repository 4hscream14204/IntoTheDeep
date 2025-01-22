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
        if(elbow.getPosition() == Elbow.ElbowPosition.PRESUBPICKUP.value && wrist.getPosition() == Wrist.WristPosition.PRESUBPICKUP.value && shoulder.isShoulderHome()) {
            addCommands(
                    new InstantCommand(() -> wrist.goToPosition(Wrist.WristPosition.PICKUP)),
                    new InstantCommand(() -> elbow.goToPosition(Elbow.ElbowPosition.PICKUP)),
                    new InstantCommand(()-> intake.intakeSpeed(0.7))
            );
        }
        else if(elbow.getPosition() == Elbow.ElbowPosition.PICKUP.value && wrist.getPosition() == Wrist.WristPosition.PICKUP.value && shoulder.isShoulderHome()){
            addCommands(
                    new InstantCommand(()-> wrist.goToPosition(Wrist.WristPosition.PRESUBPICKUP)),
                    new InstantCommand(()->elbow.goToPosition(Elbow.ElbowPosition.PRESUBPICKUP))
            );
        }
        else if(elbow.getPosition() == Elbow.ElbowPosition.HOME.value && wrist.getPosition() == Wrist.WristPosition.AUTOINIT.value && shoulder.isShoulderHome()){
            addCommands(
                    new InstantCommand(()->elbow.goToPosition(Elbow.ElbowPosition.PRESUBPICKUP)),
                    new InstantCommand(()->wrist.goToPosition(Wrist.WristPosition.PRESUBPICKUP))
            );
        }
        else if(elbow.getPosition() == Elbow.ElbowPosition.PRESUBPICKUP.value && wrist.getPosition() == Wrist.WristPosition.HOME.value){
            addCommands(
                    new InstantCommand(()->elbow.goToPosition(Elbow.ElbowPosition.PICKUP)),
                    new InstantCommand(()->wrist.goToPosition(Wrist.WristPosition.PICKUP))
            );
        }
    }
}
