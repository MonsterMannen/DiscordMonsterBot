package se.monstermannen.discordmonsterbot.commands.music;

import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
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

        StringBuilder sb = new StringBuilder();
        sb.append("``` #  Songs\n" +
                        "---------------------------------------------------------------------------------------");
        for(int i = 0; i < results.size(); i++){
            sb.append("\n");
            if(i < 10) sb.append(" ");
            sb.append(i).append(". ").append(results.get(i));
        }
        sb.append("```");

        String msg = sb.toString();
        if(msg.length() > 1999){
            msg.substring(0, 1999); // ghetto solution on too long messages
        }

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
