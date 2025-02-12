package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;

import java.util.concurrent.atomic.AtomicBoolean;

public class EjectCommandGroup extends SequentialCommandGroup {
    boolean hasRan;
    public EjectCommandGroup(RobotBase robotBase){
        if(robotBase.shoulderSubsystem.isAtPosition(Shoulder.ShoulderPosition.TOGGLE) && !robotBase.extensionSubsystem.isAtPosition(Extension.ExtensionPosition.HIGHBUCKET) && !robotBase.extensionSubsystem.isAtPosition(Extension.ExtensionPosition.LOWBUCKET)) {
            addCommands(
                    new InstantCommand(() -> robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.OPEN)),
                    //new WaitCommand(500),
                    new InstantCommand(() -> robotBase.intakeSubsystem.intakeOuttake()),
                    new WaitCommand(200),
                    new InstantCommand(() -> robotBase.intakeSubsystem.intakeStop()),
                    new InstantCommand(() -> robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.ClOSED)),
                    new InstantCommand(() -> robotBase.clawSubsystem.openClaw())
            );
        }
        else{
            addCommands(
                    new InstantCommand(() -> robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.OPEN)),
                    new InstantCommand(() -> robotBase.intakeSubsystem.intakeOuttake()),
                    new WaitCommand(200),
                    new InstantCommand(() -> robotBase.intakeSubsystem.intakeStop()),
                    new InstantCommand(() -> robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.ClOSED)),
                    new WaitCommand(500),
                    new InstantCommand(()-> robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP)),
                    new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.NEWHIGHCHAMBERCLAMP)),
                    new WaitUntilCommand(()->robotBase.extensionSubsystem.extensionGetPosition() > (Extension.ExtensionPosition.NEWHIGHCHAMBERCLAMP.height)),
                    new ParallelCommandGroup( new ShoulderHomeCommandGroup(robotBase.shoulderSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem),
                            new ExtensionHomeCommandGroup(robotBase.extensionSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem))
            );
        }
    }
}
