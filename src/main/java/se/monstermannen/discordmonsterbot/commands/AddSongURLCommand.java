package se.monstermannen.discordmonsterbot.commands;

import se.monstermannen.discordmonsterbot.Command;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Viktor on 2017-01-13.
 */
public class AddSongURLCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {

        if(args.length == 0){
            try {
                channel.sendMessage("No url specified");
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
            }
            return;
        }

        try {
            URL u = new URL(args[0]);
            DiscordMonsterBot.getPlayer(channel.getGuild()).queue(u);
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

    @Override
    public String getCommand() {
        return "addSongURL";
    }

    @Override
    public String getDescription() {
        return null;
    }

}
