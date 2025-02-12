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
        if(!robotBase.shoulderSubsystem.isShoulderHome()) {
            addCommands(
                    new ShoulderHomeCommandGroup(robotBase.shoulderSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem)
            );
        }
        if(!robotBase.extensionSubsystem.isExtensionHome()){
            addCommands(
                    new ExtensionHomeCommandGroup(robotBase.extensionSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem)
            );
        }
        addCommands(
                new WaitUntilCommand(()->robotBase.shoulderSubsystem.isShoulderHome()),
                new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.TOGGLE)),
                new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.AUTOINIT))
        );
    }
}
