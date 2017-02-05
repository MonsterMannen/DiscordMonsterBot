package se.monstermannen.discordmonsterbot.commands.music;

import com.arsenarsen.lavaplayerbridge.player.Player;
import com.arsenarsen.lavaplayerbridge.player.Track;
import com.google.api.services.youtube.YouTube;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.util.concurrent.ExecutionException;


/**
 * Add a song from youtube or local
 */
public class AddSongCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        if(args.length == 0){
            MonsterMessage.sendMessage(channel, "Specify song. Youtube link(s) or local name.");
            return;
        }

        // queue every youtube link listed
        if(args[0].contains("youtu")){
            for(String yt_link : args){
                if(yt_link.contains("youtu")){
                    queueTrack(channel, "", yt_link);
                }
            }
        }
        // queue a local song
        else{
            String songname = "";
            for(String word : args){
                songname += word + " ";
            }
            queueTrack(channel, DiscordMonsterBot.MUSICDIR + "/", songname);
        }
    }

    // queue a song
    private void queueTrack(IChannel channel, String prefix, String identifier){
        assert identifier != null && identifier != "";
        if(prefix == null) prefix = "";

        Player player = DiscordMonsterBot.getPlayer(channel.getGuild());
        AudioItem item = null;

        try {
            item = player.resolve(prefix + identifier);
        } catch (ExecutionException | InterruptedException e) {
            MonsterMessage.sendMessage(channel, "Error: " + e.getMessage());
            e.printStackTrace();
        }

        Track track = new Track((AudioTrack) item);
        player.queue(track);
    }

    @Override
    public String getCommand() {
        return "addSong";
    }

    @Override
    public String getDescription() {
        return "Add a song from youtube or a local one.\n" +
                "If multiple youtube songs are linked, all will be added.";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.GENERAL;
    }

    private boolean isInteger(String s) {
        try {
            int x = Integer.parseInt(s);
        } catch(Exception e){
            return false;
        }
        return true;
    }
}
