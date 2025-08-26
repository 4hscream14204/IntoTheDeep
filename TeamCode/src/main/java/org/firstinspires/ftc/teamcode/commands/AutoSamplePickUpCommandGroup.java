package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class AutoSamplePickUpCommandGroup extends SequentialCommandGroup {
    public AutoSamplePickUpCommandGroup (RobotBase robotBase){
        addCommands(
                new InstantCommand(()-> robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP)),
                new InstantCommand(()-> robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.HOME)),
                new InstantCommand(()-> robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.CLOSED)),
                new WaitCommand(250),
                new InstantCommand(()-> robotBase.intakeSubsystem.intakeSpeed(0.7))
        );
    }
}
