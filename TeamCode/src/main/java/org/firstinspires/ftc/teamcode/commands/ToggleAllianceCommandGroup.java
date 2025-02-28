package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.ITDCrabEnums;

public class ToggleAllianceCommandGroup extends SequentialCommandGroup {
    public ToggleAllianceCommandGroup(){
        if(DataStorage.alliance == ITDCrabEnums.EnmAlliance.BLUE){
            addCommands(
                    new InstantCommand(()-> DataStorage.alliance = ITDCrabEnums.EnmAlliance.RED)
            );
        }
        else{
            addCommands(
                    new InstantCommand(()-> DataStorage.alliance = ITDCrabEnums.EnmAlliance.BLUE)
            );
        }
    }
}
