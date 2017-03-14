package se.monstermannen.discordmonsterbot.commands.music;

import com.arsenarsen.lavaplayerbridge.player.Player;
import com.arsenarsen.lavaplayerbridge.player.Track;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;
import java.util.*;


/**
 * Playlist command
 */
public class PlaylistCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        Player player = DiscordMonsterBot.getPlayer(channel.getGuild());


        if(player.getPlayingTrack() == null){
            MonsterMessage.sendErrorMessage(channel, "Playlist empty");
            return;
        }

        AudioTrack track = player.getPlayingTrack().getTrack();
        Queue<Track> playlist = player.getPlaylist();

        StringBuilder sb = new StringBuilder();
        sb.append(track.getInfo().title + " <").append("\n");
        for(Track t : playlist){
            String songname = t.getTrack().getInfo().title;
            sb.append(songname).append("\n");
        }

        String loop = player.getLooping() ? "on" : "off";
        String status = player.getPaused() ? "paused" : "playing";

        EmbedBuilder embed = new EmbedBuilder()
                .withColor(Color.CYAN)
                .withDescription("**Playlist[" + (playlist.size()+1) + "]** - Looping: **" + loop + "** "
                        + "- Status: **" + status + "** \n\n" + sb);

        MonsterMessage.sendMessage(channel, embed.build());
    }

    @Override
    public String getCommand() {
        return "playlist";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"queue"};
    }

    @Override
    public String getDescription() {
        return "Print the songs in the current play queue.";
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.MUSIC;
    }

}
