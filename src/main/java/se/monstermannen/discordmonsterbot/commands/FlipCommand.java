package se.monstermannen.discordmonsterbot.commands;

import se.monstermannen.discordmonsterbot.Command;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * Flip a coin command
 */
public class FlipCommand implements Command {
    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        int x = (int)(Math.random() * 2);
        try {
            channel.sendMessage(x + "");
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getCommand() {
        return "flip";
    }

    @Override
    public String getDescription() {
        return "Return 1 or 0";
    }
}
