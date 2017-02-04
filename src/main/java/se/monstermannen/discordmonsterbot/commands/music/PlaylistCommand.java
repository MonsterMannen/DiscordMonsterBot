package se.monstermannen.discordmonsterbot.commands.music;

import com.arsenarsen.lavaplayerbridge.player.Track;
import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.audio.AudioPlayer;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Playlist command
 */
public class PlaylistCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        Queue<Track> playlist = DiscordMonsterBot.getPlayer(channel.getGuild()).getPlaylist();

        String msg = "";
        for(Track t : playlist){
            String songname = DiscordMonsterBot.playlist.get(t);
            if(songname.endsWith(".mp3")){
                songname = songname.substring(0, songname.length() - ".mp3".length());
            }
            msg += songname + "\n";
        }

        if(playlist.size() == 0){
            MonsterMessage.sendMessage(channel, "Playlist empty");
        }else{
            EmbedBuilder embed = new EmbedBuilder()
                    .withColor(Color.ORANGE)
                    .appendField("Playlist[" + playlist.size() + "]", msg, false);

            MonsterMessage.sendMessage(channel, embed.build());
        }
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
