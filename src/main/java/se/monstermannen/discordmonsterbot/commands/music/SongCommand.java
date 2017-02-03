package se.monstermannen.discordmonsterbot.commands.music;

import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;

/**
 * Print info on current song
 */
public class SongCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        if(DiscordMonsterBot.getPlayer(channel.getGuild()).getPlayingTrack() == null){
            MonsterMessage.sendMessage(channel, "No song playing \uD83C\uDFB5");    // musical note emoji
            return;
        }

        //String songname = DiscordMonsterBot.playlist.get(DiscordMonsterBot.getPlayer(channel.getGuild()).getPlayingTrack());
        String songname = DiscordMonsterBot.getPlayer(channel.getGuild()).getPlayingTrack().toString();

        // player volume
        int vol = DiscordMonsterBot.getPlayer(channel.getGuild()).getVolume();


        // embed message
        EmbedBuilder embed = new EmbedBuilder()
                .withColor(Color.CYAN)
                .withDescription("**" + songname + "** \n\nvol: " + vol + "%");

        MonsterMessage.sendMessage(channel, embed.build());
    }

    @Override
    public String getCommand() {
        return "song";
    }

    @Override
    public String getDescription() {
        return "Give info about current song playing";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.MUSIC;
    }

}
