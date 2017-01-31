package se.monstermannen.discordmonsterbot.commands;

import se.monstermannen.discordmonsterbot.Command;
import se.monstermannen.discordmonsterbot.CommandType;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import se.monstermannen.discordmonsterbot.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * List songs in music directory Command
 */
public class ListSongsCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        List<String> results = new ArrayList<>();

        try {
            File[] files = new File(DiscordMonsterBot.MUSICDIR).listFiles();

            for (File file : files) {
                if (file.isFile()) {
                    results.add(file.getName());
                }
            }
        }catch (Exception e){
            MonsterMessage.sendMessage(channel, "ERROR: " + e.getMessage());
            return;
        }

        String msg = "``` #  Songs\n" +
                        "---------------------------------------------------------------------------------------";

        for(int i = 0; i < results.size(); i++){
            if(msg.length() > 1900) break;  // todo do something smart here (discord msg max length 2000)
            msg += "\n";
            if(i < 10) msg += " ";
            msg += i + ". " + results.get(i);
        }
        msg += "```";

        MonsterMessage.sendMessage(channel, msg);
    }

    @Override
    public String getCommand() {
        return "listSongs";
    }

    @Override
    public String getDescription() {
        return "List every song in the music folder.";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.MUSIC;
    }

}
