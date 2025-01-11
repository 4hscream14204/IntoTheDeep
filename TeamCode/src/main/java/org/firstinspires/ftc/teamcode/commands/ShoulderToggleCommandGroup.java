package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class ShoulderToggleCommandGroup extends SequentialCommandGroup {
    public ShoulderToggleCommandGroup(RobotBase robotBase){
        if(robotBase.shoulderSubsystem.isShoulderHome() == false){
            addCommands(
                    new ShoulderHomeCommandGroup(robotBase.shoulderSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem)
            );
        }
        else if(robotBase.shoulderSubsystem.isShoulderHome() == true){
            addCommands(
                    new InstantCommand(()-> robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.TOGGLE)),
                    new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP)),
                    new InstantCommand(()-> robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP))
            );
        }
    }
}
