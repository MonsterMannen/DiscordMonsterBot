package se.monstermannen.discordmonsterbot.commands.music;

import com.arsenarsen.lavaplayerbridge.player.Track;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Queue;

/**
 * Save playqueue to playlist file
 */
public class SavePlaylistCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {

        if(args.length > 1){
            MonsterMessage.sendMessage(channel, "playlist name can't contain spaces");
            return;
        } else if(args[0].contains("/") || args[0].contains("\\")){
            MonsterMessage.sendMessage(channel, "Illegal characters");
            return;
        } else if(DiscordMonsterBot.getPlayer(channel.getGuild()).getPlayingTrack() == null){
            MonsterMessage.sendMessage(channel, "No songs in playqueue");
            return;
        }

        Queue<Track> playlist = DiscordMonsterBot.getPlayer(channel.getGuild()).getPlaylist();  // play queue
        playlist.add(DiscordMonsterBot.getPlayer(channel.getGuild()).getPlayingTrack());    // add current track

        try {
            String path = "playlists/" + args[0] + ".txt";
            PrintWriter writer = new PrintWriter(path, "UTF-8");
            for(Track track : playlist){
                String id = track.getTrack().getInfo().identifier;
                writer.println(id);
            }
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getCommand() {
        return "savePlaylist";
    }

    @Override
    public String getDescription() {
        return "Saves songs in the playqueue to a loadable playlist.";
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.MUSIC;
    }

}