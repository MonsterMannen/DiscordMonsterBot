package se.monstermannen.discordmonsterbot.commands.admin;

import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.Image;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;

/**
 * Change bots avatar command
 */
public class SetBotAvatarCommand implements Command {
    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        if(args.length == 0){
            MonsterMessage.sendMessage(channel, "No image url specified");
            return;
        }

        try {
            RequestBuffer.request(() -> {
                channel.getClient().changeAvatar(Image.forUrl("", args[0]));
            });
        } catch (DiscordException | RateLimitException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCommand() {
        return "setBotAvatar";
    }

    @Override
    public String getDescription() {
        return "Change the bots avatar to specified url";
    }

    @Override
    public CommandType getCommandType(){
        return CommandType.ADMIN;
    }
}
