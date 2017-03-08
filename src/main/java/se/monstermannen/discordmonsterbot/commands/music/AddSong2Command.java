package se.monstermannen.discordmonsterbot.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.ownaudio.GuildMusicManager;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

/**
 * test new audio manager
 */
public class AddSong2Command implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        String trackUrl = args[0];

        GuildMusicManager musicManager = DiscordMonsterBot.getGuildAudioPlayer(channel.getGuild());

        DiscordMonsterBot.getPlayerManager().loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                musicManager.scheduler.queueFirst(track);
                MonsterMessage.sendMessage(channel, "**" + track.getInfo().title + "** added.");
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }

                // "Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName()

                musicManager.scheduler.queue(firstTrack);
            }

            @Override
            public void noMatches() {
                MonsterMessage.sendErrorMessage(channel, "Nothing found by " + trackUrl);
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                MonsterMessage.sendErrorMessage(channel, "Could not play " + exception.getMessage());
            }
        });

    }

    @Override
    public String getCommand() {
        return "addSong2";
    }

    @Override
    public String getDescription() {
        return "dev testing";
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.ADMIN;
    }

}
