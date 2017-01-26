package se.monstermannen.discordmonsterbot;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

/**
 * Command interface
 */
public interface Command {

    void runCommand(IUser user, IChannel channel, IMessage message, String[] args);

    String getCommand();

    String getDescription();

    CommandType getCommandType();

}
