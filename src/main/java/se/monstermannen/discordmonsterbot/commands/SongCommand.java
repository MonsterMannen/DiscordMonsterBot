package se.monstermannen.discordmonsterbot.commands;

import se.monstermannen.discordmonsterbot.Command;
import se.monstermannen.discordmonsterbot.CommandType;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.awt.*;

/**
 * Print info on current song
 */
public class SongCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        if(DiscordMonsterBot.getPlayer(channel.getGuild()).getCurrentTrack() == null) return;

        // song title
        String songname = DiscordMonsterBot.playlist
                .get(DiscordMonsterBot.getPlayer(channel.getGuild()).getCurrentTrack());

        // player volume
        float volume = DiscordMonsterBot.getPlayer(channel.getGuild()).getVolume() * 100;
        int vol = (int) volume;

        /*
        This cool idea doesn't work well with mp3s. unlucky. maybe fix later.
         */

        /*
        // song duration bar
        long tt = DiscordMonsterBot.getPlayer(channel.getGuild()).getCurrentTrack().getTotalTrackTime();
        long ct = DiscordMonsterBot.getPlayer(channel.getGuild()).getCurrentTrack().getCurrentTrackTime();
        int percentage = (int) ((ct / tt) * 100);

        String playbar = "`";
        percentage /= 2;    // halv percentage since we use a bar of length 50 instead of 100
        System.out.println(tt + " " + ct);

        for(int i = 0; i < 50; i++){
            if(i == percentage){
                playbar += "o";
            }else {
                playbar += "-";
            }
        }
        playbar += "`";
        */

        // embed message
        EmbedBuilder embed = new EmbedBuilder()
                .withColor(Color.CYAN)
                .withDescription("**" + songname + "** \n\nvol: " + vol + "%");

        try {
            channel.sendMessage("", embed.build(), false);
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCommand() {
        return "song";
    }

    @Override
    public String getDescription() {
        return "Give info about current song playing";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.GENERAL;
    }

}
