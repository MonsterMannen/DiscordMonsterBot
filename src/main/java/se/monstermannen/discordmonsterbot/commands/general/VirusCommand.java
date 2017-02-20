package se.monstermannen.discordmonsterbot.commands.general;

import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;


/**
 * Send a link to a free movie :^)
 */
public class VirusCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        String msg = "Download and run this file to give people virus. \n"
                    + "https://monstermannen.github.io/freemovie.exe?xd=sexy_girl.jpg "
                    + MonsterMessage.getEmojiCode("joy");
        MonsterMessage.sendMessage(channel, msg);
    }

    @Override
    public String getCommand() {
        return "virus";
    }

    @Override
    public String getDescription() {
        return "Gives a link to a free movie.";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.GENERAL;
    }

}
