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

        StringBuilder sb_general = new StringBuilder();
        for(Command cmd : DiscordMonsterBot.getCommandsByType(CommandType.GENERAL)){
            sb_general.append(cmd.getCommand());
        }

        StringBuilder sb_music = new StringBuilder();
        for(Command cmd : DiscordMonsterBot.getCommandsByType(CommandType.MUSIC)){
            sb_music.append(cmd.getCommand());
        }

        StringBuilder sb_admin = new StringBuilder();
        for(Command cmd : DiscordMonsterBot.getCommandsByType(CommandType.ADMIN)){
            sb_admin.append(cmd.getCommand());
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.appendField("Help:", "Type `" + DiscordMonsterBot.PREFIX + "help [command]` for more info about specified command.", false);
        embed.appendField("General commands:", sb_general.toString(), false);
        embed.appendField("Music commands:", sb_music.toString(), false);
        embed.appendField("Admin commands:", sb_admin.toString(), false);

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

        if(found) MonsterMessage.sendMessage(channel, msg);
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
