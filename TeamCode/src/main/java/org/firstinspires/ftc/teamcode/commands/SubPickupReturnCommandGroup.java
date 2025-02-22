package org.firstinspires.ftc.teamcode.commands;

import android.provider.ContactsContract;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.ITDCrabEnums;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class SubPickupReturnCommandGroup extends SequentialCommandGroup {
    public SubPickupReturnCommandGroup(RobotBase robotBase) {
        if(DataStorage.strategy == ITDCrabEnums.Strategy.SPECIMENSTOCKPILE || DataStorage.strategy == ITDCrabEnums.Strategy.SPECIMENBASICCYCLE) {
            addCommands(
                    new InstantCommand(() -> robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PRESUBPICKUP)),
                    new InstantCommand(() -> robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PRESUBPICKUP)),
                    new ExtensionHomeCommandGroup(robotBase.extensionSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem),
                    new WaitUntilCommand(() -> robotBase.extensionSubsystem.isExtensionHome()),
                    new InstantCommand(() -> robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.TOGGLE)),
                    new InstantCommand(() -> robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.DROPOFF)),
                    new InstantCommand(() -> robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.HUMANPLAYERDROPOFF)),
                    new InstantCommand(() -> robotBase.clawSubsystem.openClaw()),
                    new WaitUntilCommand(() -> robotBase.shoulderSubsystem.isAtPosition(Shoulder.ShoulderPosition.TOGGLE)),
                    new InstantCommand(() -> robotBase.shoulderSubsystem.stopInPlace())
            );
        }
        else{
            addCommands(
                    new InstantCommand(() -> robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PRESUBPICKUP)),
                    new InstantCommand(() -> robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PRESUBPICKUP)),
                    new ExtensionHomeCommandGroup(robotBase.extensionSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem),
                    new WaitUntilCommand(() -> robotBase.extensionSubsystem.isExtensionHome()),
                    new InstantCommand(() -> robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.TOGGLE)),
                    new InstantCommand(() -> robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.DROPOFF)),
                    new InstantCommand(() -> robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.BUCKETDROPOFF)),
                    new InstantCommand(() -> robotBase.clawSubsystem.openClaw()),
                    new WaitUntilCommand(() -> robotBase.shoulderSubsystem.isAtPosition(Shoulder.ShoulderPosition.TOGGLE)),
                    new InstantCommand(() -> robotBase.shoulderSubsystem.stopInPlace())
            );
        }
    }
}
