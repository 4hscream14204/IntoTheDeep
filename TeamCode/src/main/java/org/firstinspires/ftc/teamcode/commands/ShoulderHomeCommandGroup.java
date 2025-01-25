package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class ShoulderHomeCommandGroup extends SequentialCommandGroup {
    public ShoulderHomeCommandGroup(Shoulder shoulder, Elbow elbow, Wrist wrist){
        addCommands(
                new InstantCommand(()-> shoulder.goUpOrDown(shoulder.dblDownPower)),
                new InstantCommand(()->elbow.goToPosition(Elbow.ElbowPosition.PRESUBPICKUP)),
                new InstantCommand(()->wrist.goToPosition(Wrist.WristPosition.PRESUBPICKUP)),
                new WaitUntilCommand(shoulder::isShoulderHome),
                new InstantCommand(shoulder::reset)
        );
    }
}
