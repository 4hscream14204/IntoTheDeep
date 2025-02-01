package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class ElbowWristHomeCommandGroup extends SequentialCommandGroup {
    public ElbowWristHomeCommandGroup(RobotBase robotBase){
        addCommands(
                new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.HOME)),
                new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.HOME))
        );
    }
}
