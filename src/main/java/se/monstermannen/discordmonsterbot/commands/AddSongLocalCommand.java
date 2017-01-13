package se.monstermannen.discordmonsterbot.commands;

import se.monstermannen.discordmonsterbot.Command;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.audio.AudioPlayer;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * Add song to play queue command
 */
public class AddSongLocalCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        if(args.length == 0){
            try {
                channel.sendMessage("No song specified");
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
            }
            return;
        }

        File f = new File(args[0]);
        if(!f.exists()){
            try {
                channel.sendMessage("File doesn't exist");
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
            }
            return;
        }

        AudioPlayer player = DiscordMonsterBot.getPlayer(channel.getGuild());
        try {
            final AudioPlayer.Track t = player.queue(f);    // add audio to queue
            //setTrackTitle(t, f.toString());                 // add title
        } catch (IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCommand() {
        return "addSongLocal";
    }

    @Override
    public String getDescription() {
        return "Add a song to the play queue.";
    }

}
