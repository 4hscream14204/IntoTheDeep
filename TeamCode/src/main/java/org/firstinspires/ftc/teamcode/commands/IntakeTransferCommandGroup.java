package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.opmode.teleop.TeleGeorge;
import org.firstinspires.ftc.teamcode.subsystems.Bucket;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.firstinspires.ftc.teamcode.subsystems.Zlide;

public class IntakeTransferCommandGroup extends SequentialCommandGroup {
    public IntakeTransferCommandGroup(Zlide zlide, Wrist wrist, Bucket bucket, Lift lift) {
        addCommands(
                new InstantCommand(bucket::bucketDownPosition),
                new LiftHome(lift, zlide),
                new WaitUntilCommand(lift::isHome),
                new InstantCommand(zlide::zlideBucketPosition),
                new InstantCommand(wrist::wristTransferPos)
        );
    }
}