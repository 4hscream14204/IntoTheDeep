package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.ITDCrabEnums;

public class ToggleStrategyCommandGroup extends SequentialCommandGroup {
    public ToggleStrategyCommandGroup(){
        if(DataStorage.strategy == ITDCrabEnums.Strategy.SPECIMENBASICCYCLE){
            addCommands(
                new InstantCommand(()->DataStorage.strategy = ITDCrabEnums.Strategy.SPECIMENSTOCKPILE),
                new InstantCommand(()->DataStorage.lightsChassis = ITDCrabEnums.LightsChassis.SPECIMENSTOCKPILE)
            );
        }
        else if(DataStorage.strategy == ITDCrabEnums.Strategy.SPECIMENSTOCKPILE){
            addCommands(
                    new InstantCommand(()->DataStorage.strategy = ITDCrabEnums.Strategy.BUCKETBASICCYCLE),
                    new InstantCommand(()->DataStorage.lightsChassis = ITDCrabEnums.LightsChassis.BUCKET)
            );
        }
        else{
            addCommands(
                    new InstantCommand(()->DataStorage.strategy = ITDCrabEnums.Strategy.SPECIMENBASICCYCLE),
                    new InstantCommand(()->DataStorage.lightsChassis = ITDCrabEnums.LightsChassis.SPECIMENBASIC)
            );
        }
    }
}
