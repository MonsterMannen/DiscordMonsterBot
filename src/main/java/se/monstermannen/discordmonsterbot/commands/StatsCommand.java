package se.monstermannen.discordmonsterbot.commands;

import se.monstermannen.discordmonsterbot.Command;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.awt.*;

/**
 * Give stats about the bot
 */
public class StatsCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        EmbedBuilder embed = new EmbedBuilder()
                .withAuthorName(channel.getClient().getOurUser().getDisplayName(channel.getGuild()))
                .withAuthorIcon(channel.getClient().getOurUser().getAvatarURL())
                .withColor(Color.GRAY)
                .withDesc("bot stats")
                .appendField("Servers:", "1", true)
                .appendField("Uptime:", "1", true)
                .appendField("\u200B", "\u200B", true)
                .appendField("Text Channels:", "1", true)
                .appendField("Voice Channels:", "1", true)
                .appendField("\u200B", "\u200B", true) // newline? stole from flarebot hihi :^)
                .appendField("CPU Usage:", "1", true)
                .appendField("Memory Usage:", "1", true)
                .appendField("\u200B", "\u200B", true)
                .appendField("\u200B", "\u200B", false)
                .appendField("Source: ", "[`GitHub`](https://github.com/#)", true);
        try {
            channel.sendMessage("", embed.build(), false);
        } catch (RateLimitException | DiscordException | MissingPermissionsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCommand() {
        return "stats";
    }

    @Override
    public String getDescription() {
        return "Stats about the bot.";
    }

}
