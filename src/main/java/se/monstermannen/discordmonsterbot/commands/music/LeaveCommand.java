package se.monstermannen.discordmonsterbot.commands.music;

import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
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

            if(channel.getGuild().equals(voiceChannel.getGuild())){ // check that voice channel is in correct guild
                voiceChannel.leave();
                DiscordMonsterBot.getPlayer(channel.getGuild()).setPaused(true);
            }
        }
    }

    @Override
    public String getCommand() {
        return "leave";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return "Leave voice channel.";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.MUSIC;
    }

}
