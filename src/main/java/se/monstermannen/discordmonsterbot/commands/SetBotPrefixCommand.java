package se.monstermannen.discordmonsterbot.commands;

import se.monstermannen.discordmonsterbot.Command;
import se.monstermannen.discordmonsterbot.CommandType;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * Set prefix for bot commands. Owner only.
 */
public class SetBotPrefixCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        DiscordMonsterBot.PREFIX = args[0];
        try {
            channel.sendMessage("Prefix set to " + DiscordMonsterBot.PREFIX);
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCommand() {
        return "setBotPrefix";
    }

    @Override
    public String getDescription() {
        return "Set the prefix for commands.";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.ADMIN;
    }

}
