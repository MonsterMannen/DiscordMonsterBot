package se.monstermannen.discordmonsterbot.commands.music;

import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

/**
 * Play song command
 */
public class PlayCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        DiscordMonsterBot.getPlayer(channel.getGuild()).setPaused(false);
    }

    @Override
    public String getCommand() {
        return "play";
    }

    @Override
    public String getDescription() {
        return "Start/Unpause player.";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.MUSIC;
    }

}
