package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.subsystems.Bucket;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.firstinspires.ftc.teamcode.subsystems.Zlide;

public class IntakeTransferCommandGroup extends SequentialCommandGroup {
    public IntakeTransferCommandGroup(Zlide zlide, Wrist wrist, Bucket bucket) {
        addCommands(
                new InstantCommand(bucket::bucketDownPosition),
                new InstantCommand(zlide::zlideBucketPosition),
                new InstantCommand(wrist::wristTransferPos)
        );
    }
}
