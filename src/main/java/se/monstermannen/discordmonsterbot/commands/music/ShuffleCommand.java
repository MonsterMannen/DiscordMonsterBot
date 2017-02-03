package se.monstermannen.discordmonsterbot.commands.music;

import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

/**
 * Shuffle playlist
 */
public class ShuffleCommand implements Command {
    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        DiscordMonsterBot.getPlayer(channel.getGuild()).shuffle();
    }

    @Override
    public String getCommand() {
        return "shuffle";
    }

    @Override
    public String getDescription() {
        return "Shuffles the play queue.";
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.MUSIC;
    }
}
