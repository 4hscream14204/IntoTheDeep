package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Timer extends SubsystemBase {
        public int intEndgame = 120;
        public boolean endgamePassed(int intTimerLength, ElapsedTime timer){
            boolean bolEndgamePassed = false;
            if (timer.milliseconds() == intTimerLength || timer.milliseconds() > intTimerLength){
                bolEndgamePassed = true;
            }
            return bolEndgamePassed;
        }
    }
