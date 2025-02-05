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

public class BucketEjectAndHomeCommandGroup extends SequentialCommandGroup {
    public BucketEjectAndHomeCommandGroup(RobotBase robotBase, Intake intake, Shoulder shoulder, Extension extension, Wrist wrist, EjectCommandGroup ejectCommandGroup){
        addCommands(
                new EjectCommandGroup(robotBase.intakeSubsystem, robotBase.clawSubsystem),
                new WaitCommand(500),
                new InstantCommand(()-> robotBase.elbowSubsystem.goToPosition(Elbow.ElbowPosition.PICKUP)),
                new ExtensionHomeCommandGroup(robotBase.extensionSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem),
                new WaitUntilCommand(()->robotBase.extensionSubsystem.extensionGetPosition() > Extension.ExtensionPosition.NEWHIGHCHAMBERCLAMP.height),
                new ShoulderHomeCommandGroup(robotBase.shoulderSubsystem, robotBase.elbowSubsystem, robotBase.wristSubsystem),
                new InstantCommand(()->robotBase.intakeSubsystem.gateGoToPosition(Intake.GatePosition.ClOSED)));
    }
}
