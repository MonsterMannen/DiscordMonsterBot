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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Add a song via file or url to the play queue
 */
public class AddSongCommand implements Command {

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

        // only a number. chose song from list
        if(args.length == 1 && isInteger(args[0])){
            int index = Integer.parseInt(args[0]);

            List<String> results = new ArrayList<>();
            File[] files = new File(DiscordMonsterBot.MUSICDIR).listFiles();

            for (File file : files) {
                if (file.isFile()) {
                    results.add(file.getName());
                }
            }
            String path = DiscordMonsterBot.MUSICDIR + "/" + results.get(index);

            songFromFile(channel, path);
            try {
                channel.sendMessage(results.get(index) + " added to play queue");
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
            }
            return;
        }

        // put all arguments into one string
        String song = "";
        for(String s : args){
            song += s + " ";
        }
        song = song.substring(0, song.length()-1);

        if(song.startsWith("www.") || song.startsWith("http://") || song.startsWith("https://")){
            songFromURL(channel, song);
        }else{
            songFromFile(channel, song);
        }
    }

    @Override
    public String getCommand() {
        return "addSong";
    }

    @Override
    public String getDescription() {
        return "Add a song via file or url to the play queue.";
    }

    private void songFromFile(IChannel channel, String file) {
        File f = new File(file);
        if (!f.exists()) {
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
            DiscordMonsterBot.playlist.put(t, f.getName());
        } catch (IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    private void songFromURL(IChannel channel, String url){
        try {
            URL u = new URL(url);
            AudioPlayer.Track t = DiscordMonsterBot.getPlayer(channel.getGuild()).queue(u);
            DiscordMonsterBot.playlist.put(t, "url song");
        } catch (MalformedURLException e) {
            try {
                channel.sendMessage("invalid URL");
            } catch (MissingPermissionsException | RateLimitException | DiscordException e1) {
                e1.printStackTrace();
            }
        } catch (UnsupportedAudioFileException e) {
            try {
                channel.sendMessage("invalid file type");
            } catch (MissingPermissionsException | RateLimitException | DiscordException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            try {
                channel.sendMessage("IO exception: " + e.getMessage());
            } catch (MissingPermissionsException | RateLimitException | DiscordException e1) {
                e1.printStackTrace();
            }
        }
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(Exception e){
            return false;
        }
        return true;
    }
}
