package se.monstermannen.discordmonsterbot.commands.music;

import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

/**
 * Turn on or off playlist looping
 */
public class LoopCommand implements Command {
    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        if(args.length == 0){
            String loop = DiscordMonsterBot.LOOPPLAYLIST ? "ON" : "OFF";
            String msg = "Specify if looping should be `ON` or `OFF`.\n" +
                        "Looping currently **" + loop + "**";
            MonsterMessage.sendMessage(channel, msg);
            return;
        }

        if(args[0].equalsIgnoreCase("ON")){
            DiscordMonsterBot.getPlayer(channel.getGuild()).setLooping(true);
        }else if(args[0].equalsIgnoreCase("OFF")){
            DiscordMonsterBot.getPlayer(channel.getGuild()).setLooping(false);
        }else{
            MonsterMessage.sendMessage(channel, "Argument ON|OFF");
        }

    }

    @Override
    public String getCommand() {
        return "loop";
    }

    @Override
    public String getDescription() {
        return "Turn on or off playlist looping. `loop ON|OFF`";
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.MUSIC;
    }
}
