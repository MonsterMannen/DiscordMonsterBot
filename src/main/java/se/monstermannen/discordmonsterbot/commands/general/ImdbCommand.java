package se.monstermannen.discordmonsterbot.commands.general;

import org.json.JSONObject;
import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.util.Getters;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;

/**
 * Get info from imdb about movies or series
 */
public class ImdbCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        // arg check
        if(args.length == 0){
            MonsterMessage.sendErrorMessage(channel, "Specify movie or series to search for.");
            return;
        }

        // gets args
        StringBuilder sb = new StringBuilder();
        for(String word : args){
            sb.append(word).append("+");
        }
        String search = sb.substring(0, sb.length()-1);

        // get json
        String url = "http://www.omdbapi.com/?t=" + search + "&y=&plot=short&r=json";
        JSONObject json = Getters.getJSON(url);
        String response = (String) json.get("Response");

        // print info
        if(response.equals("False")){
            MonsterMessage.sendErrorMessage(channel, "Could not find **" + search + "**");
        }else{
            String title = (String) json.get("Title");
            String year = (String) json.get("Year");
            String rating = (String) json.get("imdbRating");
            String poster = (String) json.get("Poster");
            poster = poster.equals("N/A") ? "" : poster;

            String msg = "\nYear: " + year
                    + "\nRating: " + rating;

            EmbedBuilder embed = new EmbedBuilder();
            embed.withTitle(title)
                    .appendDesc(msg)
                    .withThumbnail(poster)
                    .withColor(Color.YELLOW);

            MonsterMessage.sendMessage(channel, embed.build());
        }
    }

    @Override
    public String getCommand() {
        return "imdb";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"movie"};
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
