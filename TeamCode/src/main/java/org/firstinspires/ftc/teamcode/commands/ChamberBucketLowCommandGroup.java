package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.base.ITDCrabEnums;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class ChamberBucketLowCommandGroup extends SequentialCommandGroup {
    public ChamberBucketLowCommandGroup(RobotBase robotBase, ITDCrabEnums.ControlScheme controlScheme){
        if(controlScheme == ITDCrabEnums.ControlScheme.BUCKET){
            if(robotBase.shoulderSubsystem.isAtPosition(Shoulder.ShoulderPosition.TOGGLE) && !robotBase.extensionSubsystem.isAtPosition(Extension.ExtensionPosition.LOWBUCKET)) {
                addCommands(
                        new InstantCommand(() -> robotBase.clawSubsystem.openClaw()),
                        new InstantCommand(() -> robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.LOWBASKET)),
                        new InstantCommand(() -> robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PRESUBPICKUP)),
                        new WaitUntilCommand(() -> robotBase.shoulderSubsystem.isAtPosition(Shoulder.ShoulderPosition.LOWBASKET)),
                        new InstantCommand(() -> robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.LOWBUCKET)),
                        new InstantCommand(() -> robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.BUCKETDROPOFF)),
                        new WaitUntilCommand(() -> robotBase.shoulderSubsystem.isAtPosition(Shoulder.ShoulderPosition.TOGGLE)),
                        new InstantCommand(() -> robotBase.shoulderSubsystem.stopInPlace())
                );
            }
            if(robotBase.shoulderSubsystem.isAtPosition(Shoulder.ShoulderPosition.TOGGLE) && robotBase.extensionSubsystem.isAtPosition(Extension.ExtensionPosition.LOWBUCKET)){
                addCommands(
                        new InstantCommand(() -> robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.OPEN)),
                        new InstantCommand(() -> robotBase.intakeSubsystem.intakeOuttake()),
                        new WaitCommand(500),
                        new InstantCommand(() -> robotBase.intakeSubsystem.intakeStop()),
                        new WaitCommand(500),
                        new InstantCommand(() -> robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.CLOSED)),
                        new InstantCommand(()-> robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP)),
                        new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME)),
                        new WaitUntilCommand(()->robotBase.extensionSubsystem.extensionGetPosition() > (Extension.ExtensionPosition.HIGHCHAMBERCLAMP.height)),
                        new ParallelCommandGroup(new ShoulderHomeCommandGroup(robotBase.shoulderSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem),
                                new ExtensionHomeCommandGroup(robotBase.extensionSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem))
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
            else if(!robotBase.shoulderSubsystem.isShoulderHome() && !robotBase.extensionSubsystem.isAtPosition(Extension.ExtensionPosition.LOWCHAMBER)){
                addCommands(
                        new GyroResetCommandGroup(robotBase),
                        new InstantCommand(robotBase.clawSubsystem::closeClaw),
                        new WaitCommand(250),
                        new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.LOWCHAMBER)),
                        new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP)),
                        new WaitUntilCommand(()->robotBase.extensionSubsystem.isAtPosition(Extension.ExtensionPosition.LOWCHAMBER)),
                        new InstantCommand(robotBase.extensionSubsystem::stopInPlace),
                        new InstantCommand(robotBase.shoulderSubsystem::stopInPlace)
                );
            }
            else if(robotBase.extensionSubsystem.isAtPosition(Extension.ExtensionPosition.LOWCHAMBER) && robotBase.shoulderSubsystem.isAtPosition(Shoulder.ShoulderPosition.TOGGLE)){
                addCommands(
                        new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.NEWLOWCHAMBERCLAMP)),
                        new WaitUntilCommand(()->robotBase.extensionSubsystem.isAtPosition(Extension.ExtensionPosition.NEWLOWCHAMBERCLAMP)),
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
