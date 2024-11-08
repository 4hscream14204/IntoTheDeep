package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.subsystems.Bucket;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.firstinspires.inspection.InspectionActivity;

public class ObservationZoneDropoffCommandGroup extends SequentialCommandGroup {
    public ObservationZoneDropoffCommandGroup(Lift lift, Bucket bucket, Wrist wrist){
        addCommands(
                new InstantCommand(wrist::wristPickupPos),
                new InstantCommand(()->lift.goToPosition(Lift.LiftPosition.OBSERVATIONZONEDROPOFF)),
                new WaitUntilCommand(lift::isAtObservationZone),
                new InstantCommand(bucket::bucketUpPosition),
                new WaitCommand(2000),
                new InstantCommand(bucket::bucketDownPosition),
                new LiftHome(lift)
        );
    }
}
