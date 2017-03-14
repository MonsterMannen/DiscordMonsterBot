package se.monstermannen.discordmonsterbot.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

/**
 * Command interface
 */
public interface Command {

    void runCommand(IUser user, IChannel channel, IMessage message, String[] args);

    String getCommand();

    String[] getAliases();

    String getDescription();

    CommandType getCommandType();

}
