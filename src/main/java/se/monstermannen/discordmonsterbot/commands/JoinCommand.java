package se.monstermannen.discordmonsterbot.commands;

import se.monstermannen.discordmonsterbot.Command;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * Join voicechannel command
 */
public class JoinCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        if(user.getConnectedVoiceChannels().isEmpty()){ // user who called method not in a voicechannel
            try {
                channel.sendMessage("Join a voice channel first");
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
            }
        }else { // user in a voicechannel
            try {
                IVoiceChannel voiceChannel = user.getConnectedVoiceChannels().get(0);

                if(channel.getGuild().equals(voiceChannel.getGuild())){ // check that voicechannel is in correct guild
                    voiceChannel.join();
                }

            } catch (MissingPermissionsException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getCommand() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "Join your voicechannel.";
    }

}
