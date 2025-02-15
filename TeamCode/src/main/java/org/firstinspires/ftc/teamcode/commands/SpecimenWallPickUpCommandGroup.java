package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class SpecimenWallPickUpCommandGroup extends SequentialCommandGroup {
public SpecimenWallPickUpCommandGroup (RobotBase robotBase, Shoulder shoulder, Claw claw, Extension extension, Elbow elbow, Wrist wrist){
    if (shoulder.enmShoulderPosition == Shoulder.ShoulderPosition.TOGGLE && extension.isExtensionHome()){
        addCommands(
                new InstantCommand(claw::closeClaw),
                new WaitCommand(250),
                new InstantCommand(()->extension.goToPosition(Extension.ExtensionPosition.HIGHCHAMBER)),
                new InstantCommand(()->elbow.goToPosition(Elbow.ElbowPosition.PICKUP)),
                new WaitUntilCommand(()->extension.isAtPosition(Extension.ExtensionPosition.HIGHCHAMBER)),
                new InstantCommand(extension::stopInPlace),
                new InstantCommand(shoulder::stopInPlace)
        );
    }
    else {
        addCommands(
                new ExtensionHomeCommandGroup(extension, elbow, wrist),
                new WaitUntilCommand(extension::isExtensionHome),
                new ShoulderHomeCommandGroup(shoulder, elbow, wrist),
                new InstantCommand(()->wrist.goToPosition(Wrist.WristPosition.PICKUP)),
                new InstantCommand(()->elbow.goToPosition(Elbow.ElbowPosition.PICKUP)),
                new InstantCommand(claw::openClaw),
                //new WaitCommand(250),
                new InstantCommand(()->shoulder.goToPosition(Shoulder.ShoulderPosition.TOGGLE)),
                new WaitUntilCommand(()->shoulder.isAtPosition(Shoulder.ShoulderPosition.TOGGLE)),
                new InstantCommand(()->shoulder.stopInPlace())
        );
    }
}
}
