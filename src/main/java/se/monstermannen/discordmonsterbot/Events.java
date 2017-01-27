package se.monstermannen.discordmonsterbot;

import sun.dc.pr.PRError;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.audio.AudioPlayer;
import sx.blah.discord.util.audio.events.TrackFinishEvent;
import sx.blah.discord.util.audio.events.TrackSkipEvent;

import java.util.List;

/**
 * Handle all events
 */
public class Events {
    private static DiscordMonsterBot bot;

    public Events(DiscordMonsterBot b){
        bot = b;
    }

    // event when bot is ready
    @EventSubscriber
    public void onReady(ReadyEvent event) {
        System.out.println("Bot online!");
        bot.getClient().changeStatus(Status.stream("4chan games", "https://www.twitch.tv/phantomn00b"));   // hehe

        List<IGuild> guilds = bot.getClient().getGuilds();
        for(IGuild g : guilds){
            DiscordMonsterBot.getPlayer(g).setLoop(DiscordMonsterBot.LOOPPLAYLIST);   // set loop for all guilds
            DiscordMonsterBot.getPlayer(g).setVolume((float) 50 / 100);               // set volume to 50
        }
    }

    // event when bot reads a message
    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event) throws RateLimitException, DiscordException, MissingPermissionsException {
        IMessage message = event.getMessage();
        IChannel channel = message.getChannel();
        IUser user = message.getAuthor();
        String msg = message.getContent();
        String g = message.getGuild().getName();
        String c = message.getChannel().getName();
        String a = message.getAuthor().getName();

        System.out.printf("[%s][%s]<%s>: %s\n", g, c, a, msg); // console output
        bot.increaseReadMessages();

        // return if not correct prefix or only prefix + 1 char
        if(!msg.startsWith(DiscordMonsterBot.PREFIX) || msg.length() < DiscordMonsterBot.PREFIX.length() + 1)
            return;

        msg = msg.substring(DiscordMonsterBot.PREFIX.length());	// remove prefix character/s
        String[] args = new String[0];
        String command = msg;

        if(msg.contains(" ")){
            command = msg.substring(0, msg.indexOf(" "));           // get first word in string as command
            args = msg.substring(msg.indexOf(" ") + 1).split(" ");  // get rest of words in string as args
        }

        // run command
        for(Command cmd : DiscordMonsterBot.getCommands()){
            if(cmd.getCommand().equalsIgnoreCase(command)){
                if(cmd.getCommandType() == CommandType.ADMIN && !user.getID().equals(DiscordMonsterBot.ADMIN_ID)){
                    channel.sendMessage("Admin only command \uD83D\uDE0E"); // sunglasses smiley
                }else {
                    cmd.runCommand(user, channel, message, args);
                    bot.increaseReadCommands();
                }
            }
        }
    }

}
