package se.monstermannen.discordmonsterbot.commands.general;

import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Warnings
 */
public class WarningCommand implements Command {

    // 222222:1
    // 213312:0
    // id:warnings

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {

        HashMap<String, String> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        File file = new File("config/warnings.txt");

        try{
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                String[] split = line.split(":");
                String userID = split[0];
                String nrWarnings = split[1];
                String displayName = channel.getClient().getUserByID(userID).getDisplayName(channel.getGuild());

                map.put(userID, nrWarnings);
                sb.append(nrWarnings).append(" ").append(displayName).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(args.length == 0){
            // print warnings
            EmbedBuilder embed = new EmbedBuilder();
            embed.withTitle("Warnings");
            embed.appendDesc(sb.toString());
            MonsterMessage.sendMessage(channel, embed.build());
        }else{
            // new warning
            if(message.getMentions().size() < 1) {
                MonsterMessage.sendErrorMessage(channel, "Mention one toxic user to warn.");
                return;
            }

            IUser userToWarn = message.getMentions().get(0);

            String w = map.get(userToWarn.getID());
            if(w == null){
                w = "0";
            }
            int warns = Integer.parseInt(w) + 1;

            // update map
            map.remove(userToWarn.getID());
            map.put(userToWarn.getID(), warns+"");

            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(file, false);

                Iterator it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    writer.write(pair.getKey() + ":" + pair.getValue());
                    writer.write("\n");
                    it.remove(); // avoids a ConcurrentModificationException
                }
                writer.close();

                String n = userToWarn.getDisplayName(channel.getGuild());
                MonsterMessage.sendMessage(channel, n + " got **" + warns + "** " + (warns == 1 ? "warning." : "warnings."));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getCommand() {
        return "warning";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"warn", "warnings"};
    }

    @Override
    public String getDescription() {
        return "Warn a user for bad behaviour.";
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.GENERAL;
    }

}
