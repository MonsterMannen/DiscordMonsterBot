package se.monstermannen.discordmonsterbot.commands.music;

import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
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
        return "Pause current song.";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.MUSIC;
    }

}
