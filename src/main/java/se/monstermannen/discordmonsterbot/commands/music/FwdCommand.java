package se.monstermannen.discordmonsterbot.commands.music;

import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

/**
 * Forward song by specified amount
 */
public class FwdCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        // todo
        //DiscordMonsterBot.getPlayer(channel.getGuild()).getPlayingTrack().getTrack();
        MonsterMessage.sendMessage(channel, "not implemented");
    }

    @Override
    public String getCommand() {
        return "fdw";
    }

    @Override
    public String getDescription() {
        return "Forward current song by specified amount of seconds";
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.MUSIC;
    }

}
