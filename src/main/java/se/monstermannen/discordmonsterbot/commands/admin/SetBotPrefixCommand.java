package se.monstermannen.discordmonsterbot.commands.admin;

import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;


/**
 * Set prefix for bot commands. Owner only.
 */
public class SetBotPrefixCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        DiscordMonsterBot.PREFIX = args[0];
        MonsterMessage.sendMessage(channel, "Prefix set to " + DiscordMonsterBot.PREFIX);
    }

    @Override
    public String getCommand() {
        return "setBotPrefix";
    }

    @Override
    public String getDescription() {
        return "Set the prefix for commands.";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.ADMIN;
    }

}
