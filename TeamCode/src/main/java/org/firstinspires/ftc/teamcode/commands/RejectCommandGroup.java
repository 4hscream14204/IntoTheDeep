package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.ITDCrabEnums;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

public class RejectCommandGroup extends SequentialCommandGroup {
    public RejectCommandGroup(RobotBase robotBase){
        if (robotBase.intakeSubsystem.isColor(Intake.ColorList.BLUE) && DataStorage.alliance != ITDCrabEnums.EnmAlliance.BLUE || robotBase.intakeSubsystem.isColor(Intake.ColorList.RED) && DataStorage.alliance != ITDCrabEnums.EnmAlliance.RED) {
            addCommands(
                    new InstantCommand(()->robotBase.intakeSubsystem.intakeOuttake()),
                    new InstantCommand(()->robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.OPEN)),
                    new WaitCommand(500),
                    new InstantCommand(()->robotBase.intakeSubsystem.intakeStop()),
                    new InstantCommand(()->robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.ClOSED))
            );
        }
    }
}
