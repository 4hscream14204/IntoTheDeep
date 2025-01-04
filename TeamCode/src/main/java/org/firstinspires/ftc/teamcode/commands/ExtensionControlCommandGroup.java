package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Extension;

public class ExtensionControlCommandGroup extends SequentialCommandGroup {
    public ExtensionControlCommandGroup(RobotBase robotBase, double triggerPower){
        if(!robotBase.extensionSubsystem.isPastMaxPosition()){
            addCommands(
            new InstantCommand(()->robotBase.extensionSubsystem.extend(triggerPower))
            );
        }
        if(robotBase.extensionSubsystem.isPastMaxPosition() && robotBase.shoulderSubsystem.isShoulderHome() && triggerPower < 0.1){
            robotBase.extensionSubsystem.intMaxPosition = Extension.ExtensionPosition.MAXSHOULDERDOWNPOSITION.height;
            addCommands(
            new InstantCommand(()->robotBase.extensionSubsystem.extendMotor.setPower(0))
            );
        }
        else if(robotBase.extensionSubsystem.isPastMaxPosition() && !robotBase.shoulderSubsystem.isShoulderHome() && triggerPower < 0.1){
            robotBase.extensionSubsystem.intMaxPosition = Extension.ExtensionPosition.MAXSHOULDERUPPOSITION.height;
            addCommands(
            new InstantCommand(()->robotBase.extensionSubsystem.stopInPlace())
            );
        }
        else if(robotBase.extensionSubsystem.isPastMaxPosition() && triggerPower > 0.1){
            robotBase.extensionSubsystem.extend(triggerPower);
        }
    }
}
