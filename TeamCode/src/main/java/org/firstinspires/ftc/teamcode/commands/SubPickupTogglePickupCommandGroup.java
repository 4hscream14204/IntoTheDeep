package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class SubPickupTogglePickupCommandGroup extends SequentialCommandGroup {
    public SubPickupTogglePickupCommandGroup(RobotBase robotBase){
        addCommands(
                new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP)),
                new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.PICKUP))
        );
        /*if(robotBase.intakeSubsystem.isMyColor()){
            addCommands(
                    new InstantCommand(()->robotBase.intakeSubsystem.intakeStop())
            );
        }
        else{
            addCommands(
                    new InstantCommand(()->robotBase.intakeSubsystem.intakeOuttake()),
                    new WaitCommand(250),
                    new InstantCommand(()->robotBase.intakeSubsystem.intakeSpeed(1))
            );
        }*/
    }
}
