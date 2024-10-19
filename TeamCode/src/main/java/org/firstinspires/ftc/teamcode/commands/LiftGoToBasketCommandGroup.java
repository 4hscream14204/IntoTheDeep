package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.subsystems.Bucket;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class LiftGoToBasketCommandGroup extends SequentialCommandGroup {
    public LiftGoToBasketCommandGroup(Lift lift, Bucket bucket, Wrist wrist, Lift.LiftPosition basketPosition){
        addCommands(
            new InstantCommand(wrist::wristPickupPos),
            new InstantCommand(()->lift.goToPosition(basketPosition)),
            new WaitUntilCommand(()->lift.isAtBasket(basketPosition)),
            new WaitCommand(250),
            new InstantCommand(bucket::bucketUpPosition),
            new WaitCommand(1500),
            new InstantCommand(bucket::bucketDownPosition),
            new LiftHome(lift)
        );
    }
}
