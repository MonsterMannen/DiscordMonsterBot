package se.monstermannen.discordmonsterbot.commands;

import se.monstermannen.discordmonsterbot.Command;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Status;

/**
 * Change bots game status command
 */
public class SetBotGameCommand implements Command {
    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        if(args.length == 0){
            channel.getClient().changeStatus(Status.empty());
        }else {
            String game = "";
            for (String word : args) {
                game += word + " ";
            }
            channel.getClient().changeStatus(Status.game(game));
        }
    }

    @Override
    public String getCommand() {
        return "setBotGame";
    }

    @Override
    public String getDescription() {
        return "Changes the bots status game to specified string";
    }
}
