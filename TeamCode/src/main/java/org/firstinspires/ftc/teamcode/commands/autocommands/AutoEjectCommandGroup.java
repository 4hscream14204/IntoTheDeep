package org.firstinspires.ftc.teamcode.commands.autocommands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.EjectCommandGroup;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

public class AutoEjectCommandGroup extends SequentialCommandGroup {

    public AutoEjectCommandGroup(RobotBase robotBase) {

        addCommands(
                    new InstantCommand(() -> robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.OPEN)),
            new InstantCommand(() -> robotBase.intakeSubsystem.intakeOuttake()),
            new WaitCommand(500),
                    new InstantCommand(() -> robotBase.intakeSubsystem.intakeStop()),
            new WaitCommand(500),
                    new InstantCommand(() -> robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.ClOSED))
        );

    }
}