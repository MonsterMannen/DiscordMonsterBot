package se.monstermannen.discordmonsterbot.commands;

import se.monstermannen.discordmonsterbot.Command;
import se.monstermannen.discordmonsterbot.CommandType;
import se.monstermannen.discordmonsterbot.DiscordMonsterBot;
import se.monstermannen.discordmonsterbot.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * Set volume for the music player command
 */
public class VolumeCommand implements Command {

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        if(args.length == 0){
            MonsterMessage.sendMessage(channel, "Needs one argument");
        }else{
            if(!isNumber(args[0])) return;  // return if not number
            int vol = Integer.parseInt(args[0]);
            if(vol > 100) vol = 100;
            if(vol < 0) vol = 0;
            Float f = (float) vol / 100;
            DiscordMonsterBot.getPlayer(channel.getGuild()).setVolume(f);
        }

    }

    @Override
    public String getCommand() {
        return "volume";
    }

    @Override
    public String getDescription() {
        return "Set volume for the player.";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.MUSIC;
    }

    // return true if arg is a number
    private boolean isNumber(String s){
        try {
            int x = Integer.parseInt(s);
        } catch(Exception e){
            return false;
        }
        return true;
    }

}
