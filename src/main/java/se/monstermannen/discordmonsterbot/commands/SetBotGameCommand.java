package se.monstermannen.discordmonsterbot.commands;

import se.monstermannen.discordmonsterbot.Command;
import se.monstermannen.discordmonsterbot.CommandType;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

/**
 * Change bots game status command
 */
public class SetBotGameCommand implements Command {
    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        String game = message.getContent()
                .substring(DiscordMonsterBot.PREFIX.length() + getCommand().length());
        channel.getClient().changePlayingText(game);
    }

    @Override
    public String getCommand() {
        return "setBotGame";
    }

    @Override
    public String getDescription() {
        return "Changes the bots status game to specified string";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.ADMIN;
    }

}
