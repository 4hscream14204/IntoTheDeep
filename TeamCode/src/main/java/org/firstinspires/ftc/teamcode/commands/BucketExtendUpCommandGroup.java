package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class BucketExtendUpCommandGroup extends SequentialCommandGroup {
    public BucketExtendUpCommandGroup(RobotBase robotBase, Shoulder.ShoulderPosition basketPosition, Extension.ExtensionPosition extensionBasket){
      addCommands(
        new InstantCommand(()-> robotBase.shoulderSubsystem.goToPosition(basketPosition)),
        new InstantCommand(()-> robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.BUCKETDROPOFF)),
        new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.DROPOFF)),
        new WaitUntilCommand(()->robotBase.shoulderSubsystem.isAtPosition(basketPosition)),
        new InstantCommand(()->robotBase.intakeSubsystem.intakeSpeed(0.6)),
        new InstantCommand(()-> robotBase.extensionSubsystem.goToPosition(extensionBasket))
      );
    }
}
