package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class TeleOpStartCommandGroup extends SequentialCommandGroup {
    public TeleOpStartCommandGroup(RobotBase robotBase){
        if(robotBase.shoulderSubsystem.isShoulderHome()){
            addCommands(
                    new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PRESUBPICKUP)),
                    new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PRESUBPICKUP))
            );
        }
        else{
            addCommands(
                    new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP)),
                    new WaitCommand(500),
                    new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP)),
                    new WaitCommand(250),
                    new ShoulderHomeCommandGroup(robotBase.shoulderSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem)
            );
        }
        if(!robotBase.extensionSubsystem.isExtensionHome()){
            addCommands(
                    new ExtensionHomeCommandGroup(robotBase.extensionSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem)
            );
        }
    }
}
