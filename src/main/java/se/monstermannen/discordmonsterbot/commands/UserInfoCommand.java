package se.monstermannen.discordmonsterbot.commands;

import se.monstermannen.discordmonsterbot.Command;
import se.monstermannen.discordmonsterbot.CommandType;
import se.monstermannen.discordmonsterbot.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;
import java.util.List;

/**
 * Give info about a user
 */
public class UserInfoCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        List<IUser> mentions = message.getMentions();
        if(mentions.isEmpty()){
            MonsterMessage.sendMessage(channel, "No user mentioned");
            return;
        }

        IUser u = mentions.get(0);
        String msg = "";
        try {
            msg = "\nUser: \t\t\t" + u.getName() + "#" + u.getDiscriminator()
                        + "\nCreated: \t" + u.getCreationDate().toString()
                        + "\nJoined: \t" + channel.getGuild().getJoinTimeForUser(u).toString()
                        + "\nID: \t\t" + u.getID()
                        + "\nAvatar: \t" + u.getAvatarURL();

        } catch (DiscordException e) {
            e.printStackTrace();
        }

        EmbedBuilder embed = new EmbedBuilder()
                .withColor(Color.MAGENTA)
                .withThumbnail(u.getAvatarURL())
                .appendField(u.getDisplayName(channel.getGuild()), msg, false);

        MonsterMessage.sendMessage(channel, embed.build());

    }

    @Override
    public String getCommand() {
        return "userinfo";
    }

    @Override
    public String getDescription() {
        return "Give info about mentioned user.";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.GENERAL;
    }

}
