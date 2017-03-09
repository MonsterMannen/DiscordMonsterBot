package se.monstermannen.discordmonsterbot.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static se.monstermannen.discordmonsterbot.DiscordMonsterBot.*;

/**
 * Read and write properties
 */
public class PropertyHandler {
    public static void readProperties(){
        try {
            FileReader reader = new FileReader("config/config.properties");
            Properties properties = new Properties();
            properties.load(reader);

            TOKEN = properties.getProperty("bot_token", TOKEN);
            YT_APIKEY = properties.getProperty("yt_apikey", YT_APIKEY);
            PREFIX = properties.getProperty("prefix", PREFIX);
            LOOPPLAYLIST = Boolean.parseBoolean(properties.getProperty("loop", LOOPPLAYLIST + ""));

            FileReader reader2 = new FileReader("config/stats.properties");
            properties.load(reader2);
            String rm =  properties.getProperty("scanned_messages", readMessages + "");
            readMessages = Integer.parseInt(rm);

            String rc =  properties.getProperty("scanned_commands", readCommands + "");
            readCommands = Integer.parseInt(rc);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveProperties(){
        Properties properties = new Properties();
        properties.setProperty("scanned_messages", readMessages + "");
        properties.setProperty("scanned_commands", readCommands + "");

        try {
            FileWriter writer = new FileWriter("config/stats.properties");
            properties.store(writer, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
