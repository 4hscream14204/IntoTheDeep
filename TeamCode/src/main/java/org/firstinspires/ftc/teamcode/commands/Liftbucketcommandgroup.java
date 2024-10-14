package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.subsystems.Bucket;
import org.firstinspires.ftc.teamcode.subsystems.Lift;

public class Liftbucketcommandgroup extends SequentialCommandGroup {
    public Liftbucketcommandgroup(Lift lift, Bucket bucket){
        addCommands(
                new InstantCommand(()->lift.goToPosition(Lift.LiftPosition.HIGHBASKET)),
            new WaitUntilCommand(lift::isHighBasket),
            new WaitCommand(1000),
            new InstantCommand(bucket::bucketUpPosition),
            new WaitCommand(500),
            new InstantCommand(bucket::bucketDownPosition),
                new InstantCommand(lift::home)
        );
}
}
