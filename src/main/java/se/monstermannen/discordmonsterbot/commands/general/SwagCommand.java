package se.monstermannen.discordmonsterbot.commands.general;

import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

/**
 * A fun command printing the awww yeeeah meme
 */
public class SwagCommand implements Command {

    // inner class to run in new thread
    protected class Swag implements Runnable {
        protected IChannel channel;

        public Swag(IChannel channel){
            this.channel = channel;
        }

        @Override
        public void run() {
            try {
                IMessage message = MonsterMessage.sendMessage(channel, "(•_•) ");
                Thread.sleep(1000);

                MonsterMessage.editMessage(message,"( •_•)>⌐■-■ ");
                Thread.sleep(1000);

                MonsterMessage.editMessage(message, "(⌐■_■) ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void runCommand(IUser user, IChannel channel, IMessage message, String[] args) {
        Thread t = new Thread(new Swag(channel));
        t.start();
    }

    @Override
    public String getCommand() {
        return "swag";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return "fun command :D";
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.GENERAL;
    }

}
