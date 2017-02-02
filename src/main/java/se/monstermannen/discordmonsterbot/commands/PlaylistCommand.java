package se.monstermannen.discordmonsterbot.commands;

import se.monstermannen.discordmonsterbot.Command;
import se.monstermannen.discordmonsterbot.CommandType;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.*;
import sx.blah.discord.util.audio.AudioPlayer;

import java.awt.*;
import java.util.List;

/**
 * Playlist command
 */
public class PlaylistCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        List<AudioPlayer.Track> playlist = DiscordMonsterBot.getPlayer(channel.getGuild()).getPlaylist();

        String msg = "";
        for(AudioPlayer.Track t : playlist){
            String songname = DiscordMonsterBot.playlist.get(t);
            if(songname.endsWith(".mp3")){
                songname = songname.substring(0, songname.length() - ".mp3".length());
            }
            msg += songname + "\n";
        }

        try {
            if(playlist.size() == 0){
                channel.sendMessage("Playlist empty");
            }else{
                EmbedBuilder embed = new EmbedBuilder()
                        .withColor(Color.ORANGE)
                        .appendField("Playlist[" + playlist.size() + "]", msg, false);

                channel.sendMessage("", embed.build(), false);
            }
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
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
        return CommandType.GENERAL;
    }

}
