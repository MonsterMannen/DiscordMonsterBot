package se.monstermannen.discordmonsterbot.commands;

import se.monstermannen.discordmonsterbot.Command;
import se.monstermannen.discordmonsterbot.CommandType;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * Send a link to a free movie :^)
 */
public class VirusCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        try {
            channel.sendMessage("https://monstermannen.github.io/freemovie.exe?xd=sexy_girl.jpg \uD83D\uDE02"); // laughing emoji code
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCommand() {
        return "virus";
    }

    @Override
    public String getDescription() {
        return "Gives a link to a free movie.";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.GENERAL;
    }

}
