package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.Bucket;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.firstinspires.ftc.teamcode.subsystems.Zlide;

public class OuttakeIntoBucketCommandGroup extends SequentialCommandGroup {
    public OuttakeIntoBucketCommandGroup(Lift lift, Bucket bucket, Zlide zlide, Wrist wrist, Intake intake){
        addCommands(
                new LiftHome(lift),
                new InstantCommand(bucket::bucketDownPosition),
                new InstantCommand(wrist::wristTransferPos),
                new InstantCommand(zlide::zlideBucketPosition),
                new WaitCommand(500),
                new InstantCommand(()->intake.intakeSpeed(-1)),
                new WaitCommand(1000),
                new InstantCommand(wrist::wristPickupPos)
        );
    }
}
