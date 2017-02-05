package se.monstermannen.discordmonsterbot.commands.music;

import com.arsenarsen.lavaplayerbridge.player.Player;
import com.arsenarsen.lavaplayerbridge.player.Track;
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
        Queue<Track> playlist = DiscordMonsterBot.getPlayer(channel.getGuild()).getPlaylist();
        Player player = DiscordMonsterBot.getPlayer(channel.getGuild());

        if(playlist.size() == 0){
            MonsterMessage.sendMessage(channel, "Playlist empty");
            return;
        }

        String msg = "";
        for(Track t : playlist){
            String songname = t.getTrack().getInfo().title;
            if(songname.endsWith(".mp3")){
                songname = songname.substring(0, songname.length() - ".mp3".length());
            }
            msg += songname + "\n";
        }

        EmbedBuilder embed = new EmbedBuilder()
                .withColor(Color.ORANGE)
                .appendField("Playlist[" + playlist.size() + "]", msg, false);

        MonsterMessage.sendMessage(channel, embed.build());
    }

    @Override
    public String getCommand() {
        return "playlist";
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
