package se.monstermannen.discordmonsterbot.util;

import com.vdurmont.emoji.EmojiManager;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.*;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Send messages from this class. Request buffer everything
 */
public class MonsterMessage {

    public static IMessage sendMessage(IChannel channel, String msg){
        // max 2000 chars
        String finalMsg = msg.substring(0, Math.min(1999, msg.length()));

        RequestBuffer.RequestFuture<IMessage> m = RequestBuffer.request(() -> {
            try {
                return channel.sendMessage(finalMsg);
            } catch (MissingPermissionsException | DiscordException e) {
                System.err.println(e.getCause() + " " + e.getMessage());
                return null;
            }
        });
        return m.get();
    }
    
    public static IMessage sendMessage(IChannel channel, EmbedObject embed){
        // check for overflow here too?

        RequestBuffer.RequestFuture<IMessage> m = RequestBuffer.request(() -> {
            try{
                return channel.sendMessage("", embed, false);
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
                return null;
            }
        });
        return m.get();
    }

    public static IMessage sendPM(IUser user, String msg){
        String finalMsg = msg.substring(0, Math.min(1999, msg.length()));

        RequestBuffer.RequestFuture<IMessage> m = RequestBuffer.request(() -> {
            try{
                return user.getOrCreatePMChannel().sendMessage(finalMsg);
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
                return null;
            }
        });
        return m.get();
    }

    public static IMessage sendFile(IChannel channel, String msg, String file){
        RequestBuffer.RequestFuture<IMessage> m = RequestBuffer.request(() -> {
            try{
                File f = new File(file);
                return channel.sendFile(msg, f);
            } catch (MissingPermissionsException | RateLimitException | DiscordException | FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        });
        return m.get();
    }

    // add a warning emoji before the message
    public static IMessage sendErrorMessage(IChannel channel, String msg){
        RequestBuffer.RequestFuture<IMessage> m = RequestBuffer.request(() -> {
            try{
                String prefix = getEmojiCode("warning");
                return sendMessage(channel, prefix + " " + msg);
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
                return null;
            }
        });
        return m.get();
    }

    public static void editMessage(IMessage msg, String newContent) {
        try {
            RequestBuffer.request(() -> {
                msg.edit(newContent);
            });
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
    }

    public static void addReaction(IMessage message, String emoji_string){
        try{
            RequestBuffer.request(() -> {
                String e = getEmojiCode(emoji_string);
                message.addReaction(e);
            });

        }catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
    }

    public static String getEmojiCode(String emoji_string){
        com.vdurmont.emoji.Emoji emoji = EmojiManager.getForAlias(emoji_string);
        return emoji.getUnicode();
    }

    // check if a string is a number
    public static boolean isInteger(String s) {
        try {
            int x = Integer.parseInt(s);
        } catch(Exception e){
            return false;
        }
        return true;
    }

}
