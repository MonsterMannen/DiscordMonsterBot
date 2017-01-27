package se.monstermannen.discordmonsterbot;

import se.monstermannen.discordmonsterbot.commands.*;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.audio.AudioPlayer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by Viktor on 2017-01-12.
 *
 * main class
 */
public class DiscordMonsterBot {
    private static String TOKEN = token.TOKEN;    // bot token in secret file token.java
    public static String PREFIX = "!";	// prefix for commands
    public static String MUSICDIR = "E:/Musik";   // directory with songs
    public static boolean LOOPPLAYLIST = false;
    public static String ADMIN_ID = "101041126537973760";
    private static IDiscordClient client;
    private static ArrayList<Command> commands = new ArrayList<>();
    public static HashMap<AudioPlayer.Track, String> playlist = new HashMap<>();
    public static MonsterTimer timer;
    private static int readMessages;
    private static int readCommands;
    public static long startTime;

    public static void main(String[] args){
        try {
            DiscordMonsterBot bot = new DiscordMonsterBot();
            DiscordMonsterBot.startTime = System.currentTimeMillis();
            DiscordMonsterBot.readProperties();
            timer = new MonsterTimer();
            client = new ClientBuilder().withToken(TOKEN).build();	// builds client
            client.getDispatcher().registerListener(new Events(bot));	// add listener
            client.login();											         // login :^)

            // add all commands
            commands.add(new HelpCommand());
            commands.add(new HelloCommand());
            commands.add(new JoinCommand());
            commands.add(new LeaveCommand());
            commands.add(new PausCommand());
            commands.add(new PlayCommand());
            commands.add(new SkipCommand());
            commands.add(new VolumeCommand());
            commands.add(new AddSongCommand());
            commands.add(new ListSongsCommand());
            commands.add(new SongCommand());
            commands.add(new PlaylistCommand());
            commands.add(new UserInfoCommand());
            commands.add(new VirusCommand());
            commands.add(new FlipCommand());
            commands.add(new StatsCommand());

            // admin only commands
            commands.add(new SetBotGameCommand());
            commands.add(new SetBotAvatarCommand());
            commands.add(new SetBotPrefixCommand());

        } catch (DiscordException | RateLimitException e) {
            e.printStackTrace();
        }
    }

    // todo show playlist command
    // todo make date format better (userinfo)
    // todo better YT download. google api? skip bat file?
    // todo logger


    public static String getUptime(){
        long totalsec = (System.currentTimeMillis() - startTime) / 1000;
        long hours = totalsec / 3600;
        long minutes = (totalsec / 60) % 60;
        long seconds = totalsec % 60;
        String ret = (hours < 10 ? "0" : "") + hours + "h "
                + (minutes < 10 ? "0"  : "") + minutes + "m "
                + (seconds < 10 ? "0" : "") + seconds + "s";

        return ret;
    }

    private static void readProperties(){
        try {
            FileReader reader = new FileReader("config/config.properties");
            Properties properties = new Properties();
            properties.load(reader);

            TOKEN = properties.getProperty("bot_token", TOKEN);
            ADMIN_ID = properties.getProperty("adminID", ADMIN_ID);
            PREFIX = properties.getProperty("prefix", PREFIX);
            MUSICDIR = properties.getProperty("music_directory", MUSICDIR);
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

    public static int getReadmessages(){
        return readMessages;
    }

    public static int getReadCommands(){
        return readCommands;
    }

    public void increaseReadMessages(){
        readMessages++;
    }

    public void increaseReadCommands(){
        readCommands++;
    }

    public IDiscordClient getClient(){
        return client;
    }

    // return commandlist
    public static ArrayList<Command> getCommands(){
        return commands;
    }

    // return the player
    public static AudioPlayer getPlayer(IGuild guild) {
        return AudioPlayer.getAudioPlayerForGuild(guild);
    }
}
