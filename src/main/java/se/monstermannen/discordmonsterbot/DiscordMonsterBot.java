package se.monstermannen.discordmonsterbot;

import com.arsenarsen.lavaplayerbridge.PlayerManager;
import com.arsenarsen.lavaplayerbridge.libraries.LibraryFactory;
import com.arsenarsen.lavaplayerbridge.libraries.UnknownBindingException;
import com.arsenarsen.lavaplayerbridge.player.Player;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.commands.admin.SetBotAvatarCommand;
import se.monstermannen.discordmonsterbot.commands.admin.SetBotGameCommand;
import se.monstermannen.discordmonsterbot.commands.admin.SetBotNameCommand;
import se.monstermannen.discordmonsterbot.commands.admin.SetBotPrefixCommand;
import se.monstermannen.discordmonsterbot.commands.general.*;
import se.monstermannen.discordmonsterbot.commands.music.*;
import se.monstermannen.discordmonsterbot.util.MonsterTimer;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RateLimitException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by Viktor on 2017-01-12.
 *
 * main class
 */
public class DiscordMonsterBot {
    // default values
    private static String TOKEN = "";           // discord bot token
    public static String YT_APIKEY = "";       // youtube api key
    public static String PREFIX = "!";          // prefix for commands
    public static boolean LOOPPLAYLIST = false; // loop music players playlist
    // objects
    private static IDiscordClient client;
    public static MonsterTimer timer;
    public static PlayerManager manager;
    public static AddSongCommand addSong;
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

            client.getDispatcher().registerListener(new Events());	// add listener
            client.login();                                         // login :^)

            // general commands
            commands.add(new HelpCommand());
            commands.add(new StatsCommand());
            commands.add(new HelloCommand());
            commands.add(new VirusCommand());
            commands.add(new FlipCommand());
            commands.add(new UserInfoCommand());
            commands.add(new WhoSpamsCommand());
            commands.add(new ImdbCommand());
            commands.add(new SwagCommand());

            // music commands
            commands.add(new JoinCommand());
            commands.add(new LeaveCommand());
            commands.add(new AddSongCommand());
            commands.add(new PlayCommand());
            commands.add(new PausCommand());
            commands.add(new SkipCommand());
            commands.add(new VolumeCommand());
            commands.add(new SongCommand());
            commands.add(new PlaylistCommand());
            commands.add(new ShuffleCommand());
            commands.add(new LoopCommand());
            commands.add(new FwdCommand());
            commands.add(new SavePlaylistCommand());

            // admin only commands (not listed when using help)
            commands.add(new SetBotGameCommand());
            commands.add(new SetBotNameCommand());
            commands.add(new SetBotAvatarCommand());
            commands.add(new SetBotPrefixCommand());

            addSong = new AddSongCommand(); // used by !play command

        } catch (DiscordException | RateLimitException | UnknownBindingException e) {
            e.printStackTrace();
        }
    }

    // todo logger
    // todo swag command edit msg (thread)
    // todo fwd song
    // todo add youtube playlist?
    // todo load playlists
    // todo cant add same song twice? can now wtf?

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

    public static int getReadmessages(){
        return readMessages;
    }

    public static int getReadCommands(){
        return readCommands;
    }

    public static void increaseReadMessages(){
        readMessages++;
    }

    public static void increaseReadCommands(){
        readCommands++;
    }

    public IDiscordClient getClient(){
        return client;
    }

    // return commandlist
    public static ArrayList<Command> getCommands(){
        return commands;
    }

    // return only commands of commandtype type
    public static ArrayList<Command> getCommandsByType(CommandType type){
        ArrayList<Command> ret = new ArrayList<>();
        for(Command cmd : commands){
            if(cmd.getCommandType() == type){
                ret.add(cmd);
            }
        }
        return ret;
    }

    // return lavaplayer
    public static Player getPlayer(IGuild guild){
        return manager.getPlayer(guild.getID());
    }
}
