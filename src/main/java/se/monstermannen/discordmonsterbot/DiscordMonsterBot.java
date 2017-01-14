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
    private static IDiscordClient client;
    private static ArrayList<Command> commands = new ArrayList<>();
    public static HashMap<AudioPlayer.Track, String> playlist = new HashMap<>();
    private static int readMessages;    // todo save to file
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
            commands.add(new SongCommand());
            commands.add(new StatsCommand());

        } catch (DiscordException | RateLimitException e) {
            e.printStackTrace();
        }
    }


    // loop playlist. check ontrackend
    // todo take all args as songname (so space in filename works)
    // todo ontrackload set songtitle as game playing
    // todo info/help command
    // todo set avatar command
    // todo userinfo @derp
    // todo check who wrote most in the last 24h?
    // todo set game func
    // todo make currentSong output cool with a volume bar :D

    public void setStatus(Status status){
        client.changeStatus(status);
    }

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
