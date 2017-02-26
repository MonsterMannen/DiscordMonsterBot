package se.monstermannen.discordmonsterbot.commands.music;

import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.io.*;

/**
 * Load a playlist into the play queue
 */
public class LoadPlaylistCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        if(args.length == 0){
            MonsterMessage.sendErrorMessage(channel, "Specify what playlist to load.");
            return;
        }

        String path = "playlists/" + args[0] + ".txt";
        File file = new File(path);
        if(!file.exists()){
            MonsterMessage.sendErrorMessage(channel, "That playlist doesn't exist.");
            return;
        }

        try {
            channel.setTypingStatus(true);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String[] line = new String[1];
            line[0] = br.readLine();
            int i = 0;

            while(line[0] != null){
                DiscordMonsterBot.addSong.runCommand(user, channel, null, line);    //null to indicate not to print stuff
                i++;
                line[0] = br.readLine();
            }

            br.close();
            channel.setTypingStatus(false);
            MonsterMessage.sendMessage(channel, "Loaded **" + i + "** songs from playlist **" + args[0] + "**");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCommand() {
        return "loadPlaylist";
    }

    @Override
    public String getDescription() {
        return "Load the songs from specified playlist into the play queue.";
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.MUSIC;
    }

}
