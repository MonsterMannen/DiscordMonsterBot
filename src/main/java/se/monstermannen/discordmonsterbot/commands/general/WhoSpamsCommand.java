package se.monstermannen.discordmonsterbot.commands.general;

import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.util.HelpMethods;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import se.monstermannen.discordmonsterbot.util.UserMsgHolder;
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
        int checkTime = 24; // default 24h

        if(args.length > 0) {
            if (HelpMethods.isInteger(args[0])) {
                checkTime = Integer.parseInt(args[0]);
            }

            boolean override = false;

            if(args.length == 2) {
                if (args[1].equals("--override-limit")) {
                    if (user.equals(channel.getClient().getApplicationOwner())) {
                        override = true;
                    } else {
                        MonsterMessage.sendErrorMessage(channel, "Admin command. This incident will be reported.");
                        return;
                    }
                }
            }

            if(checkTime > 240 && !override){
                MonsterMessage.sendErrorMessage(channel, "Max 240 hours scan (will be longer soon:tm:)");
                return;
            }
        }

        if(checkTime > 240) channel.setTypingStatus(true);

        List<UserMsgHolder> umhList = new ArrayList<>();
        IMessage msg;
        int counter;

        for(IUser u : channel.getGuild().getUsers()){
            counter = 0;
            for(IChannel c : channel.getGuild().getChannels()){

                c.getMessages().setCacheCapacity(-1);

                int i = 0;
                while(true){
                    try {
                        msg = c.getMessages().get(i++);
                        long hoursDiff = ChronoUnit.HOURS.between(msg.getTimestamp(), message.getTimestamp());
                        if(hoursDiff < checkTime){
                            if(msg.getAuthor().equals(u)){
                                counter++;
                                //System.out.println(u.getDisplayName(channel.getGuild()) + " " + counter);   // debug
                            }
                        } else {
                            break;  // stop scanning older messages
                        }
                    } catch (ArrayIndexOutOfBoundsException e){
                        break;
                    }
                }
            }
            umhList.add(new UserMsgHolder(u, counter));
        }

        Collections.sort(umhList);

        StringBuilder sb = new StringBuilder();

        for (UserMsgHolder holder : umhList) {
            if(holder.messages > 0) {
                sb.append(holder.user.getDisplayName(channel.getGuild()))
                        .append(": ")
                        .append(holder.messages)
                        .append("\n");
            }
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
    public String[] getAliases() {
        return new String[0];
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
