package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class BucketDropOffCommandGroup extends SequentialCommandGroup {
    public BucketDropOffCommandGroup (RobotBase robotBase, Shoulder.ShoulderPosition basketPosition, Extension.ExtensionPosition extensionBasket){
      addCommands(
        new InstantCommand(()-> robotBase.shoulderSubsystem.goToPosition(basketPosition)),
        new InstantCommand(()-> robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.BUCKETDROPOFF)),
        new WaitUntilCommand(()->robotBase.shoulderSubsystem.isAtPosition(basketPosition)),
        new InstantCommand(()-> robotBase.extensionSubsystem.goToPosition(extensionBasket))
       /* new WaitCommand(1500),
        new InstantCommand(()-> robotBase.intakeSubsystem.intakeOuttake()),
        new WaitCommand(2000),
        new InstantCommand(()->  robotBase.intakeSubsystem.intakeSpeed(0)),
        new InstantCommand(()-> robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.HOME)),
                new ExtensionHomeCommandGroup(robotBase.extensionSubsystem, robotBase.elbowSubsystem),
              new WaitUntilCommand(robotBase.extensionSubsystem::isExtensionHome),
        new ShoulderHomeCommandGroup(robotBase.shoulderSubsystem)

        */

      );
    }
}
