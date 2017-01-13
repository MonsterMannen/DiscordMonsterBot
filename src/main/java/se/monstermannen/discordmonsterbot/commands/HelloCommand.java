package se.monstermannen.discordmonsterbot.commands;

import se.monstermannen.discordmonsterbot.Command;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * Reply with what you said command
 */
public class HelloCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        String append = "";
        for(String word : args){
            append += word + " ";
        }
        try {
            channel.sendMessage("hello " + append);
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCommand() {
        return "hello";
    }

    @Override
    public String getDescription() {
        return "Return your message";
    }

}
