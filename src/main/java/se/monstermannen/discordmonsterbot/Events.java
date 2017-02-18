package se.monstermannen.discordmonsterbot;

import com.sedmelluq.discord.lavaplayer.player.event.TrackEndEvent;
import com.vdurmont.emoji.EmojiManager;
import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.VoiceUserSpeakingEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionAddEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelJoinEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelLeaveEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelMoveEvent;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;

import java.util.List;

/**
 * Handle bot events
 */
public class Events {
    private static DiscordMonsterBot bot;

    public Events(DiscordMonsterBot b){
        bot = b;
    }

    // event when bot is ready
    @EventSubscriber
    public void onReady(ReadyEvent event) {
        System.out.println("Bot online!");
        //bot.getClient().changeStatus(Status.stream("4chan games", "https://www.twitch.tv/phantomn00b"));   // hehe
        bot.getClient().changePlayingText("Getting reworked");

        List<IGuild> guilds = bot.getClient().getGuilds();
        for(IGuild g : guilds){
            DiscordMonsterBot.getPlayer(g).setLooping(DiscordMonsterBot.LOOPPLAYLIST);  // set loop for all guilds
            //DiscordMonsterBot.getPlayer(g).setVolume(50);                             // set volume to 50
            DiscordMonsterBot.getPlayer(g).addEventListener(new TrackEvents());
        }
    }

    // event when bot reads a message
    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event) {

        // ignore private messages
        if(event.getMessage().getGuild() == null) return;

        IMessage message = event.getMessage();
        IChannel channel = message.getChannel();
        IUser user = message.getAuthor();
        String msg = message.getContent();
        String g = message.getGuild().getName();
        String c = message.getChannel().getName();
        String a = message.getAuthor().getName();

        System.out.printf("[%s][%s]<%s>: %s\n", g, c, a, msg); // console output
        bot.increaseReadMessages();

        // return if not correct prefix or only prefix + 1 char
        if(!msg.startsWith(DiscordMonsterBot.PREFIX) || msg.length() < DiscordMonsterBot.PREFIX.length() + 1)
            return;

        msg = msg.substring(DiscordMonsterBot.PREFIX.length());	// remove prefix character/s
        String[] args = new String[0];
        String command = msg;

        if(msg.contains(" ")){
            command = msg.substring(0, msg.indexOf(" "));           // get first word in string as command
            args = msg.substring(msg.indexOf(" ") + 1).split(" ");  // get rest of words in string as args
        }

        // run command
        for(Command cmd : DiscordMonsterBot.getCommands()){
            if(cmd.getCommand().equalsIgnoreCase(command)){
                if(cmd.getCommandType() == CommandType.ADMIN && !user.getID().equals(DiscordMonsterBot.ADMIN_ID)){
                    MonsterMessage.sendMessage(channel, "Admin only command \uD83D\uDE0E"); // sunglasses smiley
                }else {
                    cmd.runCommand(user, channel, message, args);
                    bot.increaseReadCommands();
                }
            }
        }
    }
    
    @EventSubscriber
    public void onReactionAdd(ReactionAddEvent event){
        MonsterMessage.addReaction(event.getMessage(), "joy");
    }

    @EventSubscriber
    public void onJoinVoice(UserVoiceChannelJoinEvent event){
        // if bot joins a voice channel it unpauses its player
        if(event.getUser().equals(bot.getClient().getOurUser())){
            DiscordMonsterBot.getPlayer(event.getGuild()).setPaused(false);
        }
    }

    @EventSubscriber
    public void onLeaveVoice(UserVoiceChannelLeaveEvent event){
        // pause player if bot leaves or gets kicked from a voice channel
        if(event.getUser().equals(bot.getClient().getOurUser())){
            DiscordMonsterBot.getPlayer(event.getGuild()).setPaused(true);
        }
        // pause and leave if bot is left alone in a voice channel
        if(event.getVoiceChannel().getConnectedUsers().contains(bot.getClient().getOurUser())){
            if(event.getVoiceChannel().getConnectedUsers().size() < 2){
                DiscordMonsterBot.getPlayer(event.getGuild()).setPaused(true);
                event.getVoiceChannel().leave();
            }
        }
    }

    @EventSubscriber
    public void onMoveVoice(UserVoiceChannelMoveEvent event){
        // pause and leave if bot is left alone in a voice channel
        if(event.getOldChannel().getConnectedUsers().contains(event.getClient().getOurUser())){
            if(event.getOldChannel().getConnectedUsers().size() < 2){
                DiscordMonsterBot.getPlayer(event.getGuild()).setPaused(true);
                event.getOldChannel().leave();
            }
        }
    }

    @EventSubscriber
    public void onTrackEnd(TrackEndEvent event){
        System.out.println("track end test");   // test. didnt work
    }

}
