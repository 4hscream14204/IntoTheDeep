package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class AutoInitCommandGroup extends SequentialCommandGroup {
    public AutoInitCommandGroup(RobotBase robotBase){
        addCommands(
                new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PRESUBPICKUP)),
                new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PRESUBPICKUP)),
                new WaitCommand(250),
                new ShoulderHomeCommandGroup(robotBase.shoulderSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem),
                new WaitUntilCommand(()->robotBase.shoulderSubsystem.isShoulderHome()),
                new ExtensionHomeCommandGroup(robotBase.extensionSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem),
                new WaitUntilCommand(()->robotBase.extensionSubsystem.isExtensionHome()),
                new WaitCommand(250),
                new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.TOGGLE))
        );
    }
}
