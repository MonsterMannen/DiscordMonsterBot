package se.monstermannen.discordmonsterbot.commands;

import se.monstermannen.discordmonsterbot.Command;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.Image;
import sx.blah.discord.util.RateLimitException;

/**
 * Change bots avatar command
 */
public class SetBotAvatarCommand implements Command {
    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        try {
            channel.getClient().changeAvatar(Image.forUrl("", args[0]));
        } catch (DiscordException | RateLimitException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCommand() {
        return "setBotAvatar";
    }

    @Override
    public String getDescription() {
        return "Change the bots avatar to specified url";
    }
}
