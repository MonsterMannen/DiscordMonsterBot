package se.monstermannen.discordmonsterbot.util;

import se.monstermannen.discordmonsterbot.DiscordMonsterBot;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Save stats every 5 min class
 */
public class MonsterTimer {

    public MonsterTimer() {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                PropertyHandler.saveProperties();
            }
        }, 0, 5*60*1000);   // 5 min
    }

}
