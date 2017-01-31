package se.monstermannen.discordmonsterbot.commands;

import se.monstermannen.discordmonsterbot.Command;
import se.monstermannen.discordmonsterbot.CommandType;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

/**
 * Paus current song command
 */
public class PausCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        DiscordMonsterBot.getPlayer(channel.getGuild()).setPaused(true);
    }

    @Override
    public String getCommand() {
        return "paus";
    }

    @Override
    public String getDescription() {
        return "Paus current song.";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.MUSIC;
    }

}
