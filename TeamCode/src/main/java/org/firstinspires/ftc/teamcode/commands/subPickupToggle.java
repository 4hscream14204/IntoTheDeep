package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class subPickupToggle extends SequentialCommandGroup {

    boolean bolIsPreSub = false;
    public subPickupToggle(Wrist wrist, Elbow elbow){
        if (bolIsPreSub) {
            addCommands(
                    new InstantCommand(()-> wrist.goToPosition(Wrist.WristPosition.PICKUP)),
                    new InstantCommand(()-> elbow.goToPosition(Elbow.ElbowPosition.PICKUP))
            );
            bolIsPreSub = false;
        } else {
            addCommands(
                    new InstantCommand(()-> wrist.goToPosition(Wrist.WristPosition.PRESUBPICKUP)),
                    new InstantCommand(()-> elbow.goToPosition(Elbow.ElbowPosition.PRESUBPICKUP))
            );
            bolIsPreSub = true;
        }
    }
}
