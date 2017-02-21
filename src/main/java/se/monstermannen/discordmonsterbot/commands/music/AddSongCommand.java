package se.monstermannen.discordmonsterbot.commands.music;

import com.arsenarsen.lavaplayerbridge.player.Player;
import com.arsenarsen.lavaplayerbridge.player.Track;
import com.google.api.services.youtube.model.SearchResult;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import se.monstermannen.discordmonsterbot.util.YouTubeGetter;
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
            MonsterMessage.sendMessage(channel, "Specify song. One or more youtube links.");
            return;
        }

        // queue every youtube link listed
        if(args[0].contains("youtu")){
            for(String yt_link : args){
                if(yt_link.contains("youtu")){
                    queueTrack(channel, yt_link);
                }
            }
        }
        // search for youtube video
        else{
            StringBuilder sb = new StringBuilder();
            for(String word : args){
                sb.append(word).append(" ");
            }
            SearchResult ytVid = YouTubeGetter.getID(sb.toString());

            if(ytVid == null){
                MonsterMessage.sendMessage(channel, "Couldn't find any video for that search term");
                return;
            }

            String videoId = ytVid.getId().getVideoId();
            String vidTitle = ytVid.getSnippet().getTitle();

            queueTrack(channel, videoId);
            MonsterMessage.sendMessage(channel,"**" + vidTitle + "** added "
                            + MonsterMessage.getEmojiCode("musical_note"));
        }
    }

    // queue a song
    private void queueTrack(IChannel channel, String identifier){
        assert identifier != null && identifier != "";

        Player player = DiscordMonsterBot.getPlayer(channel.getGuild());
        AudioItem item = null;

        try {
            item = player.resolve(identifier);
        } catch (ExecutionException | InterruptedException e) {     // no exceptions are ever thrown?
            MonsterMessage.sendMessage(channel, "Error: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        if(item == null){
            MonsterMessage.sendMessage(channel, "Could not load track " + MonsterMessage.getEmojiCode("musical_note"));
        }else{
            Track track = new Track((AudioTrack) item);
            player.queue(track);
        }
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
        return CommandType.MUSIC;
    }

}
