package se.monstermannen.discordmonsterbot.commands;

import se.monstermannen.discordmonsterbot.Command;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * Help Command
 */
public class HelpCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        if(args.length == 0){
            help(channel);
        }else{
            if(args[0].startsWith(DiscordMonsterBot.PREFIX)){
                args[0] = args[0].substring(1); // remove potential prefix
            }
            helpCommand(channel, args[0]);
        }
    }

    private void help(IChannel channel){
        try {
            String msg = "Type `" + DiscordMonsterBot.PREFIX + "help` and a command for more info.";
            msg += "\n\nCommands:";
            for(Command cmd : DiscordMonsterBot.getCommands()){
                msg += "\n  `" + DiscordMonsterBot.PREFIX + cmd.getCommand() + "`";
            }
            channel.sendMessage(msg);

        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
    }

    private void helpCommand(IChannel channel, String command){
        String msg = "";
        boolean found = false;
        for(Command cmd : DiscordMonsterBot.getCommands()){
            if(cmd.getCommand().equals(command)){
                msg = cmd.getDescription();
                found = true;
            }
        }

        if(!found) return;

        try {
            channel.sendMessage(msg);
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCommand() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Print help info.";
    }

}
