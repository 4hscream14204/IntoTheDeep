package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

public class Timer extends SubsystemBase {
        public int intEndgame = 120;
        public boolean bolEndgamePassed;
        public boolean hasEndgamePassed(int intTimerLength){
            bolEndgamePassed = false;
            if (intEndgame == intTimerLength || intEndgame > intTimerLength){
                bolEndgamePassed = true;
            }
            return bolEndgamePassed;
        }
    }
