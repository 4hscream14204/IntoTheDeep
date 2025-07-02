package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.ITDCrabEnums;

public class ToggleControlsCommandGroup extends SequentialCommandGroup {
    public ToggleControlsCommandGroup(){
        if(DataStorage.controlScheme == ITDCrabEnums.ControlScheme.BUCKET){
            addCommands(
                   new InstantCommand(()->DataStorage.controlScheme = ITDCrabEnums.ControlScheme.SPECIMEN),
                    new InstantCommand(()->DataStorage.strategy = ITDCrabEnums.Strategy.SPECIMENSTOCKPILE)
            );
        }
        else{
            addCommands(
                    new InstantCommand(()->DataStorage.controlScheme = ITDCrabEnums.ControlScheme.BUCKET),
                    new InstantCommand(()->DataStorage.strategy = ITDCrabEnums.Strategy.BUCKETBASICCYCLE)
            );
        }
    }
}
