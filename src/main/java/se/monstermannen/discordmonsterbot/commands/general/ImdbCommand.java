package se.monstermannen.discordmonsterbot.commands.general;

import org.json.JSONObject;
import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.util.Getters;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

/**
 * Get info from imdb about movies or series
 */
public class ImdbCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        // test
        String baseUrl = "http://www.omdbapi.com/?t=troy&y=&plot=short&r=json";
        JSONObject json = Getters.getJSON(baseUrl);
        String title = (String) json.get("Title");
        System.out.println(title);
    }

    @Override
    public String getCommand() {
        return "imdb";
    }

    @Override
    public String getDescription() {
        return "Get information about specified movie or series.";
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.GENERAL;
    }

}
