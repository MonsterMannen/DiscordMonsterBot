package se.monstermannen;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.audio.AudioPlayer;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Viktor on 2017-01-12.
 *
 * main class
 */
public class DiscordMonsterBot {
    private static final String TOKEN = token.TOKEN;    // bot token in secret file token.java
    private static final String PREFIX = "!";	// prefix for commands
    private static IDiscordClient client;
    private static int readmessages = 0;    // todo save to file
    private static int readCommands = 0;
    private static int uptime = 0;  // todo fix a timer

    public static void main(String[] args){
        try {
            client = new ClientBuilder().withToken(TOKEN).build();	// builds client
            client.getDispatcher().registerListener(new DiscordMonsterBot());	// add listener
            client.login();													// login :^)
            client.changeStatus(Status.game("Hentai School 3D"));               // xD
        } catch (DiscordException | RateLimitException e) {
            e.printStackTrace();
        }

    }

    // event when bot is ready
    @EventSubscriber
    public void onReady(ReadyEvent event) {
        System.out.println("Bot is now ready!");
    }

    // event when bot reads a message
    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event) throws RateLimitException, DiscordException, MissingPermissionsException {
        IMessage message = event.getMessage();
        IChannel channel = message.getChannel();
        IUser user = message.getAuthor();
        String msg = message.getContent();
        String g = message.getGuild().getName();
        String c = message.getChannel().getName();
        String a = message.getAuthor().getName();

        System.out.printf("[%s][%s]<%s>: %s\n", g, c, a, msg); // console output
        readmessages++;

        if(!msg.startsWith(PREFIX) || msg.length() < 2) return;	// return if not correct prefix or only one char
        readCommands++;

        msg = msg.substring(1);	// remove prefix character

        if(msg.startsWith("tts")){
            printTTS(channel, message);
        }
        else if(msg.startsWith("hello")){
            hello(channel, message);
        }
        else if(msg.equals("join")){
            joinChannel(channel, user);
        }
        else if(msg.startsWith("addSongLocal")){
            addAudioFile(channel, msg.substring("addSongLocal".length()+1));
        }
        else if(msg.startsWith("addSongURL")){
            addAudioURL(channel, msg.substring("addSongURL".length()+1));
        }
        else if(msg.equals("play")){
            play(channel);
        }
        else if(msg.startsWith("paus")){
            paus(channel);
        }
        else if(msg.equals("skip")){
            skip(channel);
        }
        else if(msg.startsWith("volume")){
            int v = Integer.parseInt(msg.substring("volume".length()+1));
            volume(channel, v);
        }
        else if(msg.equals("stats")){
            scanned(channel);
        }
        else if(msg.equals("currentSong")){
            currentSong(channel);
        }
    }

    // todo info/help command
    // todo set avatar command
    // todo userinfo @derp
    // todo check who wrote most in the last 24h?
    // todo set game func
    // todo song next() use skipTo()?
    // todo make currentSong output cool with a volume bar :D

    // returns the message typed. for testing
    private void hello(IChannel channel, IMessage message) throws RateLimitException, DiscordException, MissingPermissionsException {
        channel.sendMessage(message.getContent().substring(1));
    }

    // tts message. mostly for testing
    private void printTTS(IChannel channel, IMessage message) throws RateLimitException, DiscordException, MissingPermissionsException {
        String[] s = message.getContent().split(" ");
        String msg = "";
        for(int i = 1; i < s.length; i++){
            msg += s[i] + " ";
        }
        channel.sendMessage(msg, true);
    }

    private void scanned(IChannel channel) throws RateLimitException, DiscordException, MissingPermissionsException {
        channel.sendMessage(
                "messages scanned: " + readmessages +
                        "\ncommands scanned: " + readCommands
        );
    }

    /*
    Audio
    */

    // bot joins the voice channel you are in
    private void joinChannel(IChannel channel, IUser user) throws RateLimitException, DiscordException, MissingPermissionsException {
        if(user.getConnectedVoiceChannels().isEmpty()){
            channel.sendMessage("Join a voice channel first");
            return;
        }
        IVoiceChannel voiceChannel = user.getConnectedVoiceChannels().get(0);
        voiceChannel.join();
    }

    // play audio
    private void play(IChannel channel){
        getPlayer(channel.getGuild()).setPaused(false);
    }

    // paus audio
    private void paus(IChannel channel){
        getPlayer(channel.getGuild()).setPaused(true);
    }

    // skip next song
    private void skip(IChannel channel){
        getPlayer(channel.getGuild()).skip();
    }

    // set volume
    private void volume(IChannel channel, int value){
        if(value > 100) value = 100;
        if(value < 0) value = 0;
        Float f = (float) value / 100;
        getPlayer(channel.getGuild()).setVolume(f);
    }

    // print current song name
    private void currentSong(IChannel channel) throws RateLimitException, DiscordException, MissingPermissionsException {
        if(getPlayer(channel.getGuild()).getCurrentTrack() == null) return;
        //String song = getPlayer(channel.getGuild()).getCurrentTrack().toString();
        String song = String.valueOf(getPlayer(channel.getGuild()).getCurrentTrack().getMetadata().get("title"));
        channel.sendMessage(song);
    }

    // add local media file to play queue
    private void addAudioFile(IChannel channel, String file) throws RateLimitException, DiscordException, MissingPermissionsException {
        if(file == null) return;

        File f = new File(file);
        if(!f.exists()){
            channel.sendMessage("File doesn't exist");
            return;
        }
        AudioPlayer player = getPlayer(channel.getGuild());
        try {
            final AudioPlayer.Track t = player.queue(f);    // add audio to queue
            setTrackTitle(t, f.toString());                 // add title
        } catch (IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    // add file from url to play queue
    private void addAudioURL(IChannel channel, String url) throws RateLimitException, DiscordException, MissingPermissionsException {
        try {
            URL u = new URL(url);
            getPlayer(channel.getGuild()).queue(u);
        } catch (MalformedURLException e) {
            channel.sendMessage("invalid URL");
        } catch (UnsupportedAudioFileException e) {
            channel.sendMessage("invalid file type");
        } catch (IOException e) {
            channel.sendMessage("IO exception: " + e.getMessage());
        }
    }

    // return the player
    private AudioPlayer getPlayer(IGuild guild) {
        return AudioPlayer.getAudioPlayerForGuild(guild);
    }

    // necessary? copypasted from github
    private void setTrackTitle(AudioPlayer.Track track, String title) {
        track.getMetadata().put("title", title);
    }

}
