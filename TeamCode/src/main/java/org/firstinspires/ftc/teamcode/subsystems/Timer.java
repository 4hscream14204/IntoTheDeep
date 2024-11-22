package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Timer extends SubsystemBase {
        public int intEndgame = 120;
        public boolean bolEndgamePassed;
        public boolean endgamePassed(int intTimerLength){
            bolEndgamePassed = false;
            if (intEndgame == intTimerLength || intEndgame > intTimerLength){
                bolEndgamePassed = true;
            }
            return bolEndgamePassed;
        }
    }
