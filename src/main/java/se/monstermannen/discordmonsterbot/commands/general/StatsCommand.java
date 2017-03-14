package se.monstermannen.discordmonsterbot.commands.general;

import com.sun.management.OperatingSystemMXBean;
import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;
import java.lang.management.ManagementFactory;

/**
 * Give stats about the bot
 */
public class StatsCommand implements Command {

    private Runtime runtime;
    private int mem;

    public StatsCommand(){
        runtime = Runtime.getRuntime();
    }

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        // get memory usage
        mem = (int) (runtime.totalMemory() - runtime.freeMemory());
        mem /= 1024*1024;

        // embed
        EmbedBuilder embed = new EmbedBuilder()
                .withAuthorName(channel.getClient().getOurUser().getDisplayName(channel.getGuild()))
                .withAuthorIcon(channel.getClient().getOurUser().getAvatarURL())
                .withColor(Color.GRAY)
                .withDesc("bot stats")
                .appendField("Servers:", channel.getClient().getGuilds().size() + "", true)
                .appendField("Uptime:", getUptime(), true)
                .appendField("\u200B", "\u200B", true)
                .appendField("Text Channels:", channel.getClient().getChannels(false).size() + "", true) // ignore private channels
                .appendField("Voice Connections:", channel.getClient().getConnectedVoiceChannels().size()+"", true)
                .appendField("\u200B", "\u200B", true) // newline? stole from flarebot hihi :^)
                .appendField("CPU Usage:", ((int) (ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class)
                        .getSystemCpuLoad() * 10000)) / 100f + "%", true)   // ty FlareBot
                .appendField("Memory Usage:", mem + "MB", true)
                .appendField("\u200B", "\u200B", true)
                .appendField("Read Messages:", DiscordMonsterBot.getReadmessages() + "", true)
                .appendField("Executed Commands:", DiscordMonsterBot.getReadCommands() + "", true)
                .appendField("\u200B", "\u200B", true)
                .appendField("\u200B", "\u200B", false)
                .appendField("Source: ", "[`GitHub`](https://github.com/MonsterMannen/DiscordMonsterBot)", true);

        MonsterMessage.sendMessage(channel, embed.build());
    }

    private String getUptime(){
        long totalsec = DiscordMonsterBot.getUptime();
        long hours = totalsec / 3600;
        long minutes = (totalsec / 60) % 60;
        long seconds = totalsec % 60;
        String ret = (hours < 10 ? "0" : "") + hours + "h "
                + (minutes < 10 ? "0"  : "") + minutes + "m "
                + (seconds < 10 ? "0" : "") + seconds + "s";

        return ret;
    }

    @Override
    public String getCommand() {
        return "stats";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"info"};
    }

    @Override
    public String getDescription() {
        return "Stats about the bot.";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.GENERAL;
    }

}
