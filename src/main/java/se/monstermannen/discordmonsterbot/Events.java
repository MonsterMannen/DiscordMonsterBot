package se.monstermannen.discordmonsterbot;

import com.sedmelluq.discord.lavaplayer.player.event.TrackEndEvent;
import se.monstermannen.discordmonsterbot.commands.Command;
import se.monstermannen.discordmonsterbot.commands.CommandType;
import se.monstermannen.discordmonsterbot.util.CxBanner;
import se.monstermannen.discordmonsterbot.util.HelpMethods;
import se.monstermannen.discordmonsterbot.util.MonsterMessage;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageUpdateEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionAddEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelJoinEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelLeaveEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelMoveEvent;
import sx.blah.discord.handle.obj.*;

import java.util.Arrays;
import java.util.List;

/**
 * Handle bot events
 */
public class Events {

    // event when bot is ready
    @EventSubscriber
    public void onReady(ReadyEvent event) {
        System.out.println("Bot online!");
        //bot.getClient().changeStatus(Status.stream("4chan games", "https://www.twitch.tv/phantomn00b"));   // hehe
        String status = "\uD83C\uDFB6 music \uD83C\uDFB6";  // 🎶
        event.getClient().changePlayingText(status);

        List<IGuild> guilds = event.getClient().getGuilds();
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

        // @Bot prefix = return prefix (if everyone forgets it)
        if(!event.getMessage().getMentions().isEmpty()){
            if(event.getMessage().getMentions().get(0).equals(event.getChannel().getClient().getOurUser())){
                if(event.getMessage().getContent().contains("prefix")){
                    MonsterMessage.sendMessage(event.getChannel(), DiscordMonsterBot.PREFIX);
                }
            }
        }

        IMessage message = event.getMessage();
        IChannel channel = message.getChannel();
        IUser user = message.getAuthor();
        String msg = message.getContent();
        String g = message.getGuild().getName();
        String c = message.getChannel().getName();
        String a = message.getAuthor().getName();

        System.out.printf("[%s][%s]<%s>: %s\n", g, c, a, msg); // console output
        DiscordMonsterBot.increaseReadMessages();

        CxBanner.scanAndChangeCx(message);  // prank DS. Change all Cx to xD

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
            if(cmd.getCommand().equalsIgnoreCase(command) || HelpMethods.containsIgnoreCase(cmd.getAliases(), command)){
                if(cmd.getCommandType() == CommandType.ADMIN
                        && !user.getID().equals(channel.getClient().getApplicationOwner().getID())){
                    MonsterMessage.sendMessage(channel, "Admin only command \uD83D\uDE0E"); // sunglasses smiley
                } else {
                    cmd.runCommand(user, channel, message, args);
                    DiscordMonsterBot.increaseReadCommands();
                }
            }
        }
    }

    @EventSubscriber
    public void onMessageEdit(MessageUpdateEvent event){
        CxBanner.scanAndChangeCx(event.getNewMessage());  // prank DS. Change all Cx to xD
    }
    
    @EventSubscriber
    public void onReactionAdd(ReactionAddEvent event){
        MonsterMessage.addReaction(event.getMessage(), "joy");
    }

    @EventSubscriber
    public void onJoinVoice(UserVoiceChannelJoinEvent event){
        // if bot joins a voice channel it unpauses its player
        if(event.getUser().equals(event.getClient().getOurUser())){
            DiscordMonsterBot.getPlayer(event.getGuild()).setPaused(false);
        }
    }

    @EventSubscriber
    public void onLeaveVoice(UserVoiceChannelLeaveEvent event){
        // pause player if bot leaves or gets kicked from a voice channel
        if(event.getUser().equals(event.getClient().getOurUser())){
            DiscordMonsterBot.getPlayer(event.getGuild()).setPaused(true);
        }
        // pause and leave if bot is left alone in a voice channel
        if(event.getVoiceChannel().getConnectedUsers().contains(event.getClient().getOurUser())){
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
}
