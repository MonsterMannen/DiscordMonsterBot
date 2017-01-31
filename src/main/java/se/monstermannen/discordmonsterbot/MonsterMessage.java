package se.monstermannen.discordmonsterbot;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.*;

/**
 * Send messages from this class. Request buffer everything
 */
public class MonsterMessage {

    public static IMessage sendMessage(IChannel channel, String msg){
        final IMessage[] m = new IMessage[1];
        boolean tooLong = false;
        if(msg.length() > 1999){
            tooLong = true;
            msg = msg.substring(0, 1999);
        }

        try {
            final boolean finalTooLong = tooLong;   // needs to be final
            final String finalMsg = msg;            // needs to be final
            RequestBuffer.request(() -> {           // prevents rate limit exceptions
                m[0] = channel.sendMessage(finalMsg);
                if(finalTooLong){
                    channel.sendMessage("Previous message too long. Max length is 2000 characters");
                }
            });

        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }

        return m[0];
    }
    
    public static IMessage sendMessage(IChannel channel, EmbedObject embed){
        final IMessage[] m = new IMessage[1];

        // check for overflow here too?

        try{
            RequestBuffer.request(() -> {
                m[0] = channel.sendMessage("", embed, false);
            });
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }

        return m[0];
    }

    public static IMessage sendPM(IUser user, String msg){
        final IMessage[] m = new IMessage[1];
        try{
            RequestBuffer.request(() -> {
                m[0] = user.getOrCreatePMChannel().sendMessage(msg);
            });
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
        return m[0];
    }

}
