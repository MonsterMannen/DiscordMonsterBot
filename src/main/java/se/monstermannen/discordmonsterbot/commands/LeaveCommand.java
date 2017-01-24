package se.monstermannen.discordmonsterbot.commands;

import se.monstermannen.discordmonsterbot.Command;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;


/**
 * Leave voice channel Command
 */
public class LeaveCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        if(channel.getClient().getConnectedVoiceChannels().size() > 0) {
            IVoiceChannel voiceChannel = user.getConnectedVoiceChannels().get(0);

            if(channel.getGuild().equals(voiceChannel.getGuild())){ // check that voicechannel is in correct guild
                voiceChannel.leave();
            }
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
