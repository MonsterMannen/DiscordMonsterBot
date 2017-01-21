package se.monstermannen.discordmonsterbot;

import se.monstermannen.discordmonsterbot.commands.*;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.audio.AudioPlayer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Viktor on 2017-01-12.
 *
 * main class
 */
public class DiscordMonsterBot {
    private static final String TOKEN = token.TOKEN;    // bot token in secret file token.java
    public static final String PREFIX = "!";	// prefix for commands
    public static final String MUSICDIR = "E:/Musik";   // directory with songs
    public static final boolean LOOPPLAYLIST = false;
    private static IDiscordClient client;
    private static ArrayList<Command> commands = new ArrayList<>();
    public static HashMap<AudioPlayer.Track, String> playlist = new HashMap<>();
    private static int readMessages;
    private static int readCommands;
    public static long startTime;

    public static void main(String[] args){
        try {
            DiscordMonsterBot bot = new DiscordMonsterBot();
            DiscordMonsterBot.startTime = System.currentTimeMillis();
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
            commands.add(new UserInfoCommand());
            commands.add(new SetBotGameCommand());
            commands.add(new SetBotAvatarCommand());
            commands.add(new StatsCommand());

        } catch (DiscordException | RateLimitException e) {
            e.printStackTrace();
        }
    }

    // todo show playlist command
    // todo yt download songs
    // todo properties file with #scanmsgs
    // todo check who wrote most in the last 24h?
    // todo make date format better (userinfo)
    // https://github.com/GrandPanda/RadioModule/blob/master/src/main/java/com/darichey/radiomodule/CommandQueue.java


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
