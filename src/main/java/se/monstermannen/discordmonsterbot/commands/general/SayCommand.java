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
public class SayCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        String reply = "";
        if(args.length == 0){
            reply = "xD";
        }
        else{
            reply = message.getContent().substring(message.getContent().indexOf(" "));
        }

        MonsterMessage.sendMessage(channel, reply);
    }

    @Override
    public String getCommand() {
        return "say";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"hello", "echo"};
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
