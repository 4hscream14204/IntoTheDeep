package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.ITDCrabEnums;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class ChamberBucketHighCommandGroup extends SequentialCommandGroup {
    public ChamberBucketHighCommandGroup(RobotBase robotBase, ITDCrabEnums.ControlScheme controlScheme){
        if(controlScheme == ITDCrabEnums.ControlScheme.BUCKET){
            if(!robotBase.shoulderSubsystem.isAtPosition(Shoulder.ShoulderPosition.TOGGLE) && !robotBase.extensionSubsystem.isAtPosition(Extension.ExtensionPosition.HIGHBUCKET)) {
                addCommands(
                        new InstantCommand(() -> robotBase.clawSubsystem.openClaw()),
                        new InstantCommand(() -> robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.HIGHBASKET)),
                        new InstantCommand(() -> robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PRESUBPICKUP)),
                        new WaitUntilCommand(() -> robotBase.shoulderSubsystem.isAtPosition(Shoulder.ShoulderPosition.HIGHBASKET)),
                        new InstantCommand(() -> robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHBUCKET)),
                        new InstantCommand(() -> robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.BUCKETDROPOFF)),
                        new WaitUntilCommand(() -> robotBase.shoulderSubsystem.isAtPosition(Shoulder.ShoulderPosition.TOGGLE)),
                        new InstantCommand(() -> robotBase.shoulderSubsystem.stopInPlace())
                );
            }
        }
        else{
            if(robotBase.shoulderSubsystem.isShoulderHome()){
                addCommands(
                        new ExtensionHomeCommandGroup(robotBase.extensionSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem),
                        new WaitUntilCommand(robotBase.extensionSubsystem::isExtensionHome),
                        new ShoulderHomeCommandGroup(robotBase.shoulderSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem),
                        new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP)),
                        new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP)),
                        new InstantCommand(robotBase.clawSubsystem::openClaw),
                        //new WaitCommand(250),
                        new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.TOGGLE)),
                        new WaitUntilCommand(()->robotBase.shoulderSubsystem.isAtPosition(Shoulder.ShoulderPosition.TOGGLE)),
                        new InstantCommand(()->robotBase.shoulderSubsystem.stopInPlace())
                );
            }
            else if(!robotBase.shoulderSubsystem.isShoulderHome() && !robotBase.extensionSubsystem.isAtPosition(Extension.ExtensionPosition.HIGHCHAMBER)){
                addCommands(
                        new GyroResetCommandGroup(robotBase),
                        new InstantCommand(robotBase.clawSubsystem::closeClaw),
                        new WaitCommand(250),
                        new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBER)),
                        new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP)),
                        new WaitUntilCommand(()->robotBase.extensionSubsystem.isAtPosition(Extension.ExtensionPosition.HIGHCHAMBER)),
                        new InstantCommand(robotBase.extensionSubsystem::stopInPlace),
                        new InstantCommand(robotBase.shoulderSubsystem::stopInPlace)
                );
            }
            else if(robotBase.extensionSubsystem.isAtPosition(Extension.ExtensionPosition.HIGHCHAMBER)){
                addCommands(
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHCHAMBERCLAMP)),
                        new WaitUntilCommand(()->robotBase.extensionSubsystem.isAtPosition(Extension.ExtensionPosition.HIGHCHAMBERCLAMP)),
                        new InstantCommand(()->robotBase.clawSubsystem.openClaw()),
                        new WaitCommand(250),
                        new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP)),
                        new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP)),
                    /*new ParallelCommandGroup(new ShoulderHomeCommandGroup(robotBase.shoulderSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem),
                            new ExtensionHomeCommandGroup(robotBase.extensionSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem))/*,*/
                        new ExtensionHomeCommandGroup(robotBase.extensionSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem)
                );
            }
        }
    }
}
