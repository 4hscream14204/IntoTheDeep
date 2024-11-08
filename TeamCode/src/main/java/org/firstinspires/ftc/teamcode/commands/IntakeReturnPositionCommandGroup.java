package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.subsystems.Bucket;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.firstinspires.ftc.teamcode.subsystems.Zlide;

public class IntakeReturnPositionCommandGroup extends SequentialCommandGroup {
    public IntakeReturnPositionCommandGroup(Bucket bucket, Wrist wrist, Zlide zlide){
        addCommands(
        new InstantCommand(bucket::bucketDownPosition),
        new InstantCommand(zlide::zlideBucketPosition),
        new InstantCommand(wrist::wristTransferPos)
        );
    }
}
