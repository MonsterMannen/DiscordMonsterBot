package se.monstermannen.discordmonsterbot.commands.general;

import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Give info about a user
 */
public class UserInfoCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        List<IUser> mentions = message.getMentions();
        if(mentions.isEmpty()){
            MonsterMessage.sendErrorMessage(channel, "No user mentioned");
            return;
        }

        IUser u = mentions.get(0);
        String msg = "";
        try {
            msg = "\nUser: \t" + u.getName() + "#" + u.getDiscriminator()
                        + "\nID: \t" + u.getID()
                        + "\n"
                        + "\nCreated: \t" + formatTime(u.getCreationDate())
                        + "\nJoined: \t" + formatTime(channel.getGuild().getJoinTimeForUser(u))
                        + "\n"
                        + "\nAvatar: \t" + u.getAvatarURL();

        } catch (DiscordException e) {
            e.printStackTrace();
        }

        EmbedBuilder embed = new EmbedBuilder()
                .withColor(Color.CYAN)
                .withThumbnail(u.getAvatarURL())
                .appendField(u.getDisplayName(channel.getGuild()), msg, false);

        MonsterMessage.sendMessage(channel, embed.build());

    }

    @Override
    public String getCommand() {
        return "userinfo";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"whois"};
    }

    @Override
    public String getDescription() {
        return "Give info about mentioned user.";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.GENERAL;
    }

    // return in format YYYY-MM-DD HH:SS
    private String formatTime(LocalDateTime time){
        return time.getYear() + "-" + time.getMonthValue() + "-" + time.getDayOfMonth()
                + " " + time.getMinute() + ":" + time.getSecond();
    }

}
