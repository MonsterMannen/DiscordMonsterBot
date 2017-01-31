package se.monstermannen.discordmonsterbot.commands;

import se.monstermannen.discordmonsterbot.Command;
import se.monstermannen.discordmonsterbot.CommandType;
import se.monstermannen.discordmonsterbot.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.MissingPermissionsException;

/**
 * Join voice channel command
 */
public class JoinCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        if(user.getConnectedVoiceChannels().isEmpty()){ // user who called method not in a voicechannel
            MonsterMessage.sendMessage(channel, "You must be in a voice channel first");
        }else { // user in a voicechannel
            try {
                IVoiceChannel voiceChannel = user.getConnectedVoiceChannels().get(0);

                if(channel.getGuild().equals(voiceChannel.getGuild())){ // check that voicechannel is in correct guild
                    voiceChannel.join();
                }else{
                    MonsterMessage.sendMessage(channel, "You are not connected to any voice channel in this guild.");
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

    @Override
    public CommandType getCommandType(){
        return CommandType.MUSIC;
    }

}
