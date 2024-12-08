package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class BucketDropOffCommandGroup extends SequentialCommandGroup {
    public BucketDropOffCommandGroup (Shoulder shoulder, Extension extension, Elbow elbow, Wrist wrist, Intake intake, Shoulder.ShoulderPosition basketPosition, Extension.ExtensionPosition extensionBasket){
      addCommands(
        new InstantCommand(()-> shoulder.goToPosition(basketPosition)),
                new InstantCommand(()-> wrist.goToPosition(Wrist.WristPosition.BUCKETDROPOFF)),
        new InstantCommand(()-> extension.goToPosition(extensionBasket)),
        new WaitCommand(1500),
        new InstantCommand(()-> intake.intakeOuttake()),
        new WaitCommand(2000),
        new InstantCommand(()-> wrist.goToPosition(Wrist.WristPosition.HOME)),
                new ExtensionHomeCommandGroup(extension, elbow),
        new ShoulderHomeCommandGroup(shoulder)

      );
    }
}
