package se.monstermannen.discordmonsterbot.commands.music;

import se.monstermannen.discordmonsterbot.*;
import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import se.monstermannen.discordmonsterbot.util.YTDownloader;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
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
        // no args
        if(args.length == 0){
            MonsterMessage.sendMessage(channel, "No song specified");
            return;
        }

        // youtube arg
        if(args[0].contains("youtube.com/watch?v=") || args[0].contains("youtu.be/")){
            channel.setTypingStatus(true);
            String path = YTDownloader.download(args[0]);
            System.out.println("got: " + path);
            if(path.startsWith("ERROR")){       // not sure if this works. needs to be rewritten
                MonsterMessage.sendMessage(channel, path);
                channel.setTypingStatus(false);
                return;
            }
            try {
                Thread.sleep(1000); // need to wait for song to complete? wtf?
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            channel.setTypingStatus(false);
            songFromFile(channel, path);
            return;
        }

        // number or 'random'. Chose song from list
        if(args.length == 1 && (isInteger(args[0]) || args[0].equals("random"))){
            // get a list of all songs
            List<String> results = new ArrayList<>();
            File[] files = new File(DiscordMonsterBot.MUSICDIR).listFiles();
            for (File file : files) {
                if (file.isFile() &&
                        (file.getName().endsWith(".mp3") || file.getName().endsWith(".wav") || file.getName().endsWith(".flac"))){
                    results.add(file.getName());
                }
            }


            int index = -1;

            if(isInteger(args[0])){
                index = Integer.parseInt(args[0]);
                if(index < 0 || index >= results.size()) return; // check that number is in range
            }else if(args[0].equals("random")){
                index = (int)(Math.random() * results.size());    // random number 0 to size-1
            }

            String path = DiscordMonsterBot.MUSICDIR + "/" + results.get(index);

            // add song
            songFromFile(channel, path);
            MonsterMessage.sendMessage(channel, "**" + results.get(index) + "** added to play queue");
            return;
        }

        // get here means args array contains the song name
        // put all arguments into one string
        String song = "";
        for(String s : args){
            song += s + " ";
        }
        song = song.substring(0, song.length()-1);

        if(song.startsWith("www.") || song.startsWith("http://") || song.startsWith("https://")){
            songFromURL(channel, song);
        }else{
            songFromFile(channel, DiscordMonsterBot.MUSICDIR + "/" + song); // adding music directory to path
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

    @Override
    public CommandType getCommandType(){
        return CommandType.GENERAL;
    }

    private void songFromFile(IChannel channel, String file) {
        File f = new File(file);
        if (!f.exists()) {
            MonsterMessage.sendMessage(channel, "File doesn't exist");
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
            MonsterMessage.sendMessage(channel, "Invalid URL");
        } catch (UnsupportedAudioFileException e) {
            MonsterMessage.sendMessage(channel, "Invalid file type");
        } catch (IOException e) {
            MonsterMessage.sendMessage(channel, "IO exception: " + e.getMessage());
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
