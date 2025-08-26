package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class ToggleGateCommandGroup extends SequentialCommandGroup {
    public ToggleGateCommandGroup(RobotBase robotBase) {
        if (robotBase.intakeSubsystem.isAtPosition(Intake.GatePosition.CLOSED)){
            addCommands(
                    new InstantCommand(() -> robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PRESUBPICKUP)),
                    new InstantCommand(() -> robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.BUCKETDROPOFF)),
                    new InstantCommand(() -> robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.TOGGLE)),
                    new WaitUntilCommand(() -> robotBase.shoulderSubsystem.isAtPosition(Shoulder.ShoulderPosition.TOGGLE)),
                    new InstantCommand(() -> robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.OPEN))
            );
        }
        else{
            addCommands(
                    new InstantCommand(() -> robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PRESUBPICKUP)),
                    new InstantCommand(() -> robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.BUCKETDROPOFF)),
                    new InstantCommand(() -> robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.TOGGLE)),
                    new WaitUntilCommand(() -> robotBase.shoulderSubsystem.isAtPosition(Shoulder.ShoulderPosition.TOGGLE)),
                    new InstantCommand(() -> robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.CLOSED)));
        }
    }
}
