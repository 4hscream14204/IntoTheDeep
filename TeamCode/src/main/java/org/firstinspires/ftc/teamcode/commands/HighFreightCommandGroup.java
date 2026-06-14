package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.subsystems.Lift;

public class HighFreightCommandGroup extends SequentialCommandGroup {
    public HighFreightCommandGroup(Lift lift){
        addCommands(
               new InstantCommand(()->lift.goToPosition(Lift.LiftPosition.HIGHDROPOFF)),
                new WaitUntilCommand(()->lift.isAtPosition(Lift.LiftPosition.HIGHDROPOFF)),
                new WaitCommand(200),
                new InstantCommand(()->lift.bucketServo.setPosition(0.15)),
                new WaitCommand(500),
                new InstantCommand(()->lift.bucketServo.setPosition(0.85)),
                new InstantCommand(()->lift.goToPosition(Lift.LiftPosition.HOME)),
                new WaitUntilCommand(()->lift.isAtPosition(Lift.LiftPosition.HOME)),
                //new InstantCommand(()->lift.liftMotor.setPower(0)),
                new InstantCommand(()->lift.reset())
        );
    }
}
