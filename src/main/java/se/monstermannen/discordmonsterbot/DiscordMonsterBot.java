package se.monstermannen.discordmonsterbot;

import com.arsenarsen.lavaplayerbridge.PlayerManager;
import com.arsenarsen.lavaplayerbridge.libraries.LibraryFactory;
import com.arsenarsen.lavaplayerbridge.libraries.UnknownBindingException;
import com.arsenarsen.lavaplayerbridge.player.Player;
import com.arsenarsen.lavaplayerbridge.player.Track;
import com.sedmelluq.discord.lavaplayer.source.http.HttpAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.local.LocalAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.admin.SetBotAvatarCommand;
import se.monstermannen.discordmonsterbot.commands.admin.SetBotGameCommand;
import se.monstermannen.discordmonsterbot.commands.admin.SetBotPrefixCommand;
import se.monstermannen.discordmonsterbot.commands.general.*;
import se.monstermannen.discordmonsterbot.commands.music.*;
import se.monstermannen.discordmonsterbot.util.MonsterTimer;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IGuild;
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
    // default values
    private static String TOKEN = token.TOKEN;              // bot token in secret file token.java
    public static String PREFIX = "!";                      // prefix for commands
    public static String MUSICDIR = "E:/Musik";             // directory with songs
    public static boolean LOOPPLAYLIST = false;             // loop musicplayers playlist or not
    public static String ADMIN_ID = "101041126537973760";   // discord user ID that can run admin commands
    // objects
    private static IDiscordClient client;
    public static MonsterTimer timer;
    public static PlayerManager manager;
    // variables
    private static ArrayList<Command> commands = new ArrayList<>();
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

            manager = PlayerManager.getPlayerManager(LibraryFactory.getLibrary(client));
            manager.getManager().registerSourceManager(new YoutubeAudioSourceManager());    // youtube
            manager.getManager().registerSourceManager(new LocalAudioSourceManager());      // local
            manager.getManager().registerSourceManager(new HttpAudioSourceManager());       // url

            client.getDispatcher().registerListener(new Events(bot));	// add listener
            client.login();                                             // login :^)

            // general commands
            commands.add(new HelpCommand());
            commands.add(new StatsCommand());
            commands.add(new HelloCommand());
            commands.add(new VirusCommand());
            commands.add(new FlipCommand());
            commands.add(new UserInfoCommand());

            // music commands
            commands.add(new JoinCommand());
            commands.add(new LeaveCommand());
            commands.add(new AddSongCommand());
            commands.add(new PlayCommand());
            commands.add(new PausCommand());
            commands.add(new SkipCommand());
            commands.add(new VolumeCommand());
            commands.add(new ListSongsCommand());
            commands.add(new SongCommand());
            commands.add(new PlaylistCommand());
            commands.add(new ShuffleCommand());
            commands.add(new LoopCommand());

            // admin only commands (not listed when using help)
            commands.add(new SetBotGameCommand());
            commands.add(new SetBotAvatarCommand());
            commands.add(new SetBotPrefixCommand());

        } catch (DiscordException | RateLimitException | UnknownBindingException e) {
            e.printStackTrace();
        }
    }


    // todo make date format better (userinfo)
    // todo logger
    // todo private user chat testing
    // todo IMDB command (api)
    // todo channel.getmessages. who spams? xD
    // todo empty title list when last track ended
    // todo fix addsong with number
    // todo swag command edit msg
    // todo move reactionadd to monstermessage
    // todo safer track adding
    // todo pause player when ppl leave vchannel or bot not in channel (add bot obj to tevents)
    // todo fwd song
    // todo songcommand output duration


    // return time in seconds since program start
    public static long getUptime(){
        return (System.currentTimeMillis() - startTime) / 1000;
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

    // return D4J original player
    public static AudioPlayer getOldPlayer(IGuild guild) {
        return AudioPlayer.getAudioPlayerForGuild(guild);
    }

    // return lavaplayer
    public static Player getPlayer(IGuild guild){
        return manager.getPlayer(guild.getID());
    }
}
