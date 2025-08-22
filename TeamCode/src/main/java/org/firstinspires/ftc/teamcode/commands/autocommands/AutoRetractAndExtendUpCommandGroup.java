package org.firstinspires.ftc.teamcode.commands.autocommands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.pedroPathing.commands.FollowPath;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class AutoRetractAndExtendUpCommandGroup extends SequentialCommandGroup {
    public AutoRetractAndExtendUpCommandGroup(RobotBase robotBase){
        addCommands(
                new InstantCommand(()->robotBase.shoulderSubsystem.goToPosition(Shoulder.ShoulderPosition.TOGGLE)),
                new InstantCommand(()->robotBase.intakeSubsystem.intakeSpeed(0.7)),
                new InstantCommand(()->robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PRESUBPICKUP)),
                new InstantCommand(()->robotBase.wristSubsystem.goToPosition(Wrist.WristPosition.BUCKETDROPOFF)),
                new WaitUntilCommand(()->robotBase.shoulderSubsystem.isAtPosition(Shoulder.ShoulderPosition.TOGGLE)),
                new InstantCommand(()->robotBase.extensionSubsystem.goToPosition(Extension.ExtensionPosition.HIGHBUCKET))
        );
    }
}
