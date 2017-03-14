package se.monstermannen.discordmonsterbot.commands.general;

import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

/**
 * Flip a coin command
 */
public class FlipCommand implements Command {
    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        int x = (int)(Math.random() * 2);
        MonsterMessage.sendMessage(channel, x + "");

    }

    @Override
    public String getCommand() {
        return "flip";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"random"};
    }

    @Override
    public String getDescription() {
        return "Return 1 or 0";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.GENERAL;
    }
}
