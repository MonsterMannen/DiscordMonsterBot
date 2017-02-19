package se.monstermannen.discordmonsterbot.commands.general;

import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Shows how many messages are sent from each user in the last 24h
 */
public class WhoSpamsCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        channel.setTypingStatus(true);

        int checkTime = 24; // default 24h

        if(args.length == 1){
            if(MonsterMessage.isInteger(args[0])){
                checkTime = Integer.parseInt(args[0]);
            }
        }

        HashMap<IUser, Integer> userspam = new HashMap<>();
        IMessage msg;
        int counter;

        for(IUser u : channel.getGuild().getUsers()){
            counter = 0;
            for(IChannel c : channel.getGuild().getChannels()){
                for(int i = 0; i < c.getMessages().size(); i++){
                    msg = c.getMessages().get(i);
                    long hoursDiff = ChronoUnit.HOURS.between(msg.getTimestamp(), message.getTimestamp());
                    if(hoursDiff < checkTime){
                        if(msg.getAuthor().equals(u)){
                            counter++;
                        }
                    }
                }
            }
            userspam.put(u, counter);
        }

        // add to list
        //List< ny > sortlist = new ArrayList<>();
        for (Map.Entry pair : userspam.entrySet()) {
            pair.getKey();  // iuser
            pair.getValue(); // integer
            // make a class holding both
            //sortlist.add(x);

        }

        // sort list
        /*
        Collections.sort(sortlist, new Comparator<MyObject>() {
            @Override
            public int compare(MyObject o1, MyObject o2) {
                return o1.getStartDate().compareTo(o2.getStartDate());
            }
        });
        */

        StringBuilder sb = new StringBuilder();

        for (Object o : userspam.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            IUser u = (IUser) pair.getKey();
            sb.append(u.getDisplayName(channel.getGuild()))
                    .append(": ")
                    .append(pair.getValue())
                    .append("\n");
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.withTitle("Messages sent in the last **" + checkTime + "** hours");
        embed.withDescription(sb.toString());

        channel.setTypingStatus(false);
        MonsterMessage.sendMessage(channel, embed.build());
    }

    @Override
    public String getCommand() {
        return "whoSpams";
    }

    @Override
    public String getDescription() {
        return "Show how many messages are sent from every user in the guild in the last 24h. Can specify how many hours to check.";
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.GENERAL;
    }

}
