package se.monstermannen.discordmonsterbot.util;

import sx.blah.discord.handle.obj.IMessage;

/**
 * Delete all "Cx" messages >:D
 */
public class CxBanner {
    public static void scanAndChangeCx(IMessage message){
        if(message.getContent().contains("Cx")){
            message.delete();
            MonsterMessage.sendMessage(message.getChannel(), "IcePoseidon-fanboy message deleted for everyone's safety :)");
            System.out.println("Deleted Cx message.");  // debug
        }
    }
}
