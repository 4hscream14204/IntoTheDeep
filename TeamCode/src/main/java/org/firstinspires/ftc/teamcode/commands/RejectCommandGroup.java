package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.ITDCrabEnums;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

public class RejectCommandGroup extends SequentialCommandGroup {
    public RejectCommandGroup(RobotBase robotBase){
        if(robotBase.shoulderSubsystem.isShoulderHome() && robotBase.elbowSubsystem.isAtPosition(Elbow.ElbowPosition.PICKUP)) {
            addCommands(
                    new InstantCommand(() -> robotBase.intakeSubsystem.intakeSpeed(0.8)),
                    new InstantCommand(() -> robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.OPEN)),
                    new WaitCommand(500),
                    new InstantCommand(() -> robotBase.intakeSubsystem.intakeStop()),
                    new InstantCommand(() -> robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.ClOSED))
            );
        }
    }
}
