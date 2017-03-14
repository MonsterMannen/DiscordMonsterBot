package se.monstermannen.discordmonsterbot.commands.music;

import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

/**
 * Play song command
 */
public class PlayCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        if(args.length > 0){
            DiscordMonsterBot.addSong.runCommand(user, channel, message, args); // addsong
        }else{
            // can only play if bot is connected to a voice channel
            if(channel.getGuild().getConnectedVoiceChannel() != null) {
                DiscordMonsterBot.getPlayer(channel.getGuild()).setPaused(false);   // unpause
            }
        }
    }

    @Override
    public String getCommand() {
        return "play";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"unpause", "unpaus"};
    }

    @Override
    public String getDescription() {
        return "Start/Unpause player or add a song if any is specified.";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.MUSIC;
    }

}
