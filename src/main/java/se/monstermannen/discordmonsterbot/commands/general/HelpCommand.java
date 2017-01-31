package se.monstermannen.discordmonsterbot.commands.general;

import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

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
                args[0] = args[0].substring(DiscordMonsterBot.PREFIX.length()); // remove potential prefix
            }
            helpCommand(channel, args[0]);
        }
    }

    private void help(IChannel channel){
        String msg = "Type `" + DiscordMonsterBot.PREFIX + "help` and a command for more info.";
        msg += "\n\nCommands:";
        for(Command cmd : DiscordMonsterBot.getCommands()){
            if(cmd.getCommandType() != CommandType.ADMIN) {
                msg += "\n  `" + DiscordMonsterBot.PREFIX + cmd.getCommand() + "`";
            }
        }
        MonsterMessage.sendMessage(channel, msg);
    }

    private void helpCommand(IChannel channel, String command){
        String msg = "";
        boolean found = false;
        for(Command cmd : DiscordMonsterBot.getCommands()){
            if(cmd.getCommand().equalsIgnoreCase(command)){
                msg = cmd.getDescription();
                found = true;
            }
        }

        if(!found) return;

        MonsterMessage.sendMessage(channel, msg);
    }

    @Override
    public String getCommand() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Print help info.";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.GENERAL;
    }

}
