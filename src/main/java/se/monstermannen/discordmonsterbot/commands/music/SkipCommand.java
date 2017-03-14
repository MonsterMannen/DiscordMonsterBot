package se.monstermannen.discordmonsterbot.commands.music;

import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

/**
 * Skip current song command
 */
public class SkipCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        DiscordMonsterBot.getPlayer(channel.getGuild()).skip();
    }

    @Override
    public String getCommand() {
        return "skip";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"next"};
    }

    @Override
    public String getDescription() {
        return "Skip to next song.";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.MUSIC;
    }

}
