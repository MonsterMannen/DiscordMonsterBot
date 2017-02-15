package se.monstermannen.discordmonsterbot.commands.general;

import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

/**
 * Reply with what you said command
 */
public class HelloCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        MonsterMessage.sendMessage(channel, message.getContent());
    }

    @Override
    public String getCommand() {
        return "hello";
    }

    @Override
    public String getDescription() {
        return "Echoes your message.";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.GENERAL;
    }

}
