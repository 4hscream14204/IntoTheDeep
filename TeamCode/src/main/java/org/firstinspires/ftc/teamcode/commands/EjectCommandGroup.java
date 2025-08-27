package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.ITDCrabEnums;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

import java.util.concurrent.atomic.AtomicBoolean;

public class EjectCommandGroup extends SequentialCommandGroup {
    boolean hasRan;
    public EjectCommandGroup(RobotBase robotBase){
        /*if(robotBase.shoulderSubsystem.enmShoulderPosition == Shoulder.ShoulderPosition.TOGGLE && !robotBase.extensionSubsystem.isAtPosition(Extension.ExtensionPosition.HIGHBUCKET) && !robotBase.extensionSubsystem.isAtPosition(Extension.ExtensionPosition.LOWBUCKET)) {
            robotBase.ledSubsystem.intSpecimensToDeliver ++;
            addCommands(
                    new InstantCommand(() -> robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.OPEN)),
                    //new WaitCommand(500),
                    new InstantCommand(() -> robotBase.intakeSubsystem.intakeOuttake()),
                    new WaitCommand(200),
                    new InstantCommand(() -> robotBase.intakeSubsystem.intakeStop()),
                    new InstantCommand(() -> robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.ClOSED)),
                    new InstantCommand(() -> robotBase.clawSubsystem.openClaw())
            );
        }*/
        if(DataStorage.strategy == ITDCrabEnums.Strategy.SPECIMENSTOCKPILE){
            addCommands(
                    new InstantCommand(()->robotBase.ledSubsystem.intSpecimensToDeliver ++),
                    new InstantCommand(() -> robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.OPEN)),
                    new InstantCommand(() -> robotBase.intakeSubsystem.intakeSpeed(1)),
                    new WaitCommand(200),
                    new InstantCommand(() -> robotBase.intakeSubsystem.intakeStop()),
                    new InstantCommand(()-> robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP)),
                    new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME)),
                    new WaitUntilCommand(()->robotBase.extensionSubsystem.extensionGetPosition() > (Extension.ExtensionPosition.SECONDLEVELASCENTPULL.height)),
                    new ParallelCommandGroup(new ShoulderHomeCommandGroup(robotBase.shoulderSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem),
                            new ExtensionHomeCommandGroup(robotBase.extensionSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem)),
                    new InstantCommand(() -> robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.CLOSED))
            );
        }
        else if(DataStorage.strategy == ITDCrabEnums.Strategy.SPECIMENBASICCYCLE){
            addCommands(
                    new InstantCommand(() -> robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.OPEN)),
                    new InstantCommand(() -> robotBase.intakeSubsystem.intakeOuttake()),
                    new WaitCommand(200),
                    new InstantCommand(() -> robotBase.intakeSubsystem.intakeStop()),
                    new InstantCommand(() -> robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.CLOSED)),
                    new InstantCommand(() -> robotBase.clawSubsystem.openClaw()),
                    new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP)),
                    new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP))
            );
        }
        else{
            addCommands(
                    new InstantCommand(() -> robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.OPEN)),
                    new InstantCommand(() -> robotBase.intakeSubsystem.intakeOuttake()),
                    new WaitCommand(500),
                    new InstantCommand(() -> robotBase.intakeSubsystem.intakeStop()),
                    new InstantCommand(() -> robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.CLOSED)),
                    new InstantCommand(()-> robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP)),
                    new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HOME)),
                    new WaitUntilCommand(()->robotBase.extensionSubsystem.extensionGetPosition() > (Extension.ExtensionPosition.HIGHCHAMBERCLAMP.height)),
                    new ParallelCommandGroup(new ShoulderHomeCommandGroup(robotBase.shoulderSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem),
                            new ExtensionHomeCommandGroup(robotBase.extensionSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem))
            );
        }
    }
}
