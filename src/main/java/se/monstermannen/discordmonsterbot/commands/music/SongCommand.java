package se.monstermannen.discordmonsterbot.commands.music;

import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

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

        MonsterMessage.sendMessage(channel, embed.build());
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
        return CommandType.MUSIC;
    }

}
