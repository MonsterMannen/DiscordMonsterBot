package se.monstermannen.discordmonsterbot.commands;

import com.arsenarsen.lavaplayerbridge.PlayerManager;
import com.arsenarsen.lavaplayerbridge.libraries.LibraryFactory;
import com.arsenarsen.lavaplayerbridge.libraries.UnknownBindingException;
import com.arsenarsen.lavaplayerbridge.player.Player;
import com.arsenarsen.lavaplayerbridge.player.Track;
import com.sedmelluq.discord.lavaplayer.source.local.LocalAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.util.concurrent.ExecutionException;

/**
 * Test
 */
public class TestCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {

        String yt_id = "aNzCDt2eidg";
        String local_song = "/home/viktor/Music/Kasger & Limitless - Miles Away.mp3";

        try {
            PlayerManager manager = PlayerManager.getPlayerManager(LibraryFactory.getLibrary(channel.getClient()));

            manager.getManager().registerSourceManager(new YoutubeAudioSourceManager());
            manager.getManager().registerSourceManager(new LocalAudioSourceManager());

            Player player = manager.getPlayer(channel.getGuild().getID());

            AudioItem item = player.resolve(local_song);

            Track track = new Track((AudioTrack) item);

            player.queue(track);

            player.play();


        } catch (UnknownBindingException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        /*
        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);

        // set playermanager settings?

        AudioPlayer player = playerManager.createPlayer();
        // one player per guild?

        TrackScheduler trackScheduler = new TrackScheduler();
        player.addListener(trackScheduler);

        // YT id. second one correct
        String yt = "https://youtu.be/aNzCDt2eidg";
        String yt2 = "aNzCDt2eidg";

        playerManager.loadItem(yt2, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                MonsterMessage.sendMessage(channel, "dur " + track.getDuration());
                trackScheduler.queue(track);
                player.playTrack(track);    // test
                MonsterMessage.sendMessage(channel, "track loaded with lavaplayer");
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                for (AudioTrack track : playlist.getTracks()) {
                    trackScheduler.queue(track);
                }
            }

            @Override
            public void noMatches() {
                // Notify the user that we've got nothing
                MonsterMessage.sendMessage(channel, "no match lava");
            }

            @Override
            public void loadFailed(FriendlyException throwable) {
                // Notify the user that everything exploded
                MonsterMessage.sendMessage(channel, "load failed lava");
            }
        });


        */
    }

    @Override
    public String getCommand() {
        return "test";
    }

    @Override
    public String getDescription() {
        return "Dev testing.";
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.GENERAL;
    }

}
