package se.monstermannen.discordmonsterbot.commands;

import se.monstermannen.discordmonsterbot.Command;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;


/**
 * Leave voice channel Command
 */
public class LeaveCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        if(channel.getClient().getConnectedVoiceChannels().size() > 0) {
            channel.getClient().getConnectedVoiceChannels().get(0).leave();
        }
    }

    @Override
    public String getCommand() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "Leave voice channel.";
    }

}
