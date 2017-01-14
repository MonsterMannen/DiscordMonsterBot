package se.monstermannen.discordmonsterbot;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Status;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

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
        bot.getClient().changeStatus(Status.game("Hentai School 3D"));   // hehe
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

        if(!msg.startsWith(DiscordMonsterBot.PREFIX) || msg.length() < 2) return;	// return if not correct prefix or only one char

        msg = msg.substring(1);	// remove prefix character
        String[] args = new String[0];
        String command = msg;

        if(msg.contains(" ")){
            command = msg.substring(0, msg.indexOf(" "));           // get first word in string as command
            args = msg.substring(msg.indexOf(" ") + 1).split(" ");  // get rest of words in string as args
        }

        // run command
        for(Command cmd : DiscordMonsterBot.getCommands()){
            if(cmd.getCommand().equals(command)){
                cmd.runCommand(user, channel, message, args);
                bot.increaseReadCommands();
            }
        }
    }
}
