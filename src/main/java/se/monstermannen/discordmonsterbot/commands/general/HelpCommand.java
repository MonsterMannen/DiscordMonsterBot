package se.monstermannen.discordmonsterbot.commands.general;

import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

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

        String general_commands = "";
        for(Command cmd : DiscordMonsterBot.getCommands()){
            if(cmd.getCommandType() == CommandType.GENERAL){
                general_commands += cmd.getCommand() + "\n";
            }
        }

        String music_commands = "";
        for(Command cmd : DiscordMonsterBot.getCommands()){
            if(cmd.getCommandType() == CommandType.MUSIC){
                music_commands += cmd.getCommand() + "\n";
            }
        }

        String admin_commands = "";
        for(Command cmd : DiscordMonsterBot.getCommands()){
            if(cmd.getCommandType() == CommandType.ADMIN){
                admin_commands += cmd.getCommand() + "\n";
            }
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.appendField("Help:", "Type `" + DiscordMonsterBot.PREFIX + "help [command]` for more info about specified command.", false);
        embed.appendField("General commands:", general_commands, false);
        embed.appendField("Music commands:", music_commands, false);
        embed.appendField("Admin commands:", admin_commands, false);

        MonsterMessage.sendMessage(channel, embed.build());
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
