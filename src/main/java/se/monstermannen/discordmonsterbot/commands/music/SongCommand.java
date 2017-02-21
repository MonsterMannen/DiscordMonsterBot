package se.monstermannen.discordmonsterbot.commands.music;

import com.arsenarsen.lavaplayerbridge.player.Player;
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
        Player player = DiscordMonsterBot.getPlayer(channel.getGuild());

        if(player.getPlayingTrack() == null){
            MonsterMessage.sendErrorMessage(channel, "No song playing " + MonsterMessage.getEmojiCode("musical_note"));    // musical note emoji
            return;
        }

        String songname = player.getPlayingTrack().getTrack().getInfo().title;
        int vol = player.getVolume();

        long playedAmount = player.getPlayingTrack().getTrack().getPosition();
        long totalAmount = player.getPlayer().getPlayingTrack().getDuration();
        double d = (double) playedAmount / totalAmount;

        // bar
        int length = 30;
        int pre = (int) (length * d);
        int post = length - pre;

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i = 0; i < pre; i++){
            sb.append("-");
        }
        sb.append((int)(d*100) + "%");
        for(int i = 0; i < post; i++){
            sb.append("-");
        }
        sb.append("]");


        // embed message
        EmbedBuilder embed = new EmbedBuilder()
                .withColor(Color.CYAN)
                .withDescription("**" + songname + "** \n\n" + sb + "\n\nvol: " + vol + "%");

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
