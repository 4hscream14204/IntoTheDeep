package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class EjectCommandGroup extends SequentialCommandGroup {
    public EjectCommandGroup(Intake intake){
        addCommands(
                new InstantCommand(()-> intake.gateGoToPosition(Intake.GatePosition.OPEN)),
                new InstantCommand(()-> intake.intakeOuttake()),
                new InstantCommand(()-> intake.gateGoToPosition(Intake.GatePosition.ClOSED))
        );
    }
}
