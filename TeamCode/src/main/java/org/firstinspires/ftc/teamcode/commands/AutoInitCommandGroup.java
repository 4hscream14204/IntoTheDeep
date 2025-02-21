package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class AutoInitCommandGroup extends SequentialCommandGroup {
    public AutoInitCommandGroup(RobotBase robotBase){
        addCommands(
                new InstantCommand(()->robotBase.clawSubsystem.closeClaw()),
                new WaitCommand(1000),
                new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.AUTOPARK)),
                new WaitUntilCommand(()->robotBase.shoulderSubsystem.isAtPosition(Shoulder.ShoulderPosition.AUTOPARK)),
                new InstantCommand(()->robotBase.shoulderSubsystem.setPower(0.0))

        );
    }
}
