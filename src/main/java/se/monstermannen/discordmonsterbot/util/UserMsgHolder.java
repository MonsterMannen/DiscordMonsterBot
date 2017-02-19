package se.monstermannen.discordmonsterbot.util;

import sx.blah.discord.handle.obj.IUser;

/**
 * Used by whospams-command
 */
public class UserMsgHolder implements Comparable<UserMsgHolder> {
    public IUser user;
    public Integer messages;

    public UserMsgHolder(IUser user, Integer messages){
        this.user = user;
        this.messages = messages;
    }

    @Override
    public int compareTo(UserMsgHolder umh) {
        // decending order
        return umh.messages - this.messages;
    }
}
