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
import se.monstermannen.discordmonsterbot.util.PropertyHandler;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RateLimitException;

import java.util.ArrayList;

/**
 * Created by Viktor on 2017-01-12.
 *
 * main class
 */
public class DiscordMonsterBot {
    // default values
    public static String TOKEN = "";           // discord bot token
    public static String YT_APIKEY = "";       // youtube api key
    public static String PREFIX = "!";          // prefix for commands
    public static boolean LOOPPLAYLIST = false; // loop music players playlist
    // discord
    private static IDiscordClient client;
    private static MonsterTimer timer;
    private static PlayerManager manager;
    public static AddSongCommand addSong;
    // variables
    private static ArrayList<Command> commands = new ArrayList<>();
    public static int readMessages;
    public static int readCommands;
    private static long startTime;

    public static void main(String[] args){
        try {
            DiscordMonsterBot bot = new DiscordMonsterBot();
            DiscordMonsterBot.startTime = System.currentTimeMillis();
            PropertyHandler.readProperties();
            timer = new MonsterTimer();
            client = new ClientBuilder().withToken(TOKEN).build();	// builds client

            manager = PlayerManager.getPlayerManager(LibraryFactory.getLibrary(client));
            manager.getManager().registerSourceManager(new YoutubeAudioSourceManager());    // youtube

            client.getDispatcher().registerListener(new Events());	// add listener
            client.login();                                         // login :^)

            // general commands
            commands.add(new HelpCommand());
            commands.add(new StatsCommand());
            commands.add(new SayCommand());
            commands.add(new VirusCommand());
            commands.add(new FlipCommand());
            commands.add(new UserInfoCommand());
            commands.add(new WhoSpamsCommand());
            commands.add(new ImdbCommand());
            commands.add(new SwagCommand());
            commands.add(new WarningCommand());

            // music commands
            commands.add(new JoinCommand());
            commands.add(new LeaveCommand());
            commands.add(new AddSongCommand());
            commands.add(new PlayCommand());
            commands.add(new PauseCommand());
            commands.add(new SkipCommand());
            commands.add(new ClearCommand());
            commands.add(new VolumeCommand());
            commands.add(new SongCommand());
            commands.add(new PlaylistCommand());
            commands.add(new ShuffleCommand());
            commands.add(new LoopCommand());
            commands.add(new FwdCommand());
            commands.add(new SavePlaylistCommand());
            commands.add(new LoadPlaylistCommand());

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
    // todo fwd song
    // todo random song list? + random song add
    // todo rip (text on img)
    // todo warn user
    // todo aws amazon host? heroku?
    // help -admin commands +music guide


    // return time in seconds since program start
    public static long getUptime(){
        return (System.currentTimeMillis() - startTime) / 1000;
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
