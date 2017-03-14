package se.monstermannen.discordmonsterbot.commands.admin;

import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

/**
 * Change bot name
 */
public class SetBotNameCommand implements Command {
    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        if(args.length == 0) return;

        String newName = message.getContent()
                .substring(DiscordMonsterBot.PREFIX.length() + getCommand().length());

        channel.getClient().changeUsername(newName);
    }

    @Override
    public String getCommand() {
        return "setBotName";
    }

    @Override
    public String getDescription() {
        return "Changes the bots name.";
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.ADMIN;
    }
}
