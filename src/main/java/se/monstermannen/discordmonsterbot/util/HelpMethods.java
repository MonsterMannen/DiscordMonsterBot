package se.monstermannen.discordmonsterbot.util;

import com.vdurmont.emoji.EmojiManager;

/**
 * good name :^)
 */
public class HelpMethods {

    // check if a string is a number
    public static boolean isInteger(String s) {
        try {
            int x = Integer.parseInt(s);
        } catch(Exception e){
            return false;
        }
        return true;
    }

    // get unicode for emoji
    public static String getEmojiCode(String emoji_string){
        com.vdurmont.emoji.Emoji emoji = EmojiManager.getForAlias(emoji_string);
        return emoji.getUnicode();
    }

    // check if an array contains a string
    public static boolean containsIgnoreCase(String[] array, String word){
        for(String s : array){
            if(s.equalsIgnoreCase(word)){
                return true;
            }
        }
        return false;
    }
}
