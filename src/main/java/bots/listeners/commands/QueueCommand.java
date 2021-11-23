package bots.listeners.commands;

import bots.helpers.AudioManager;
import bots.helpers.LavaPlayerAudioSource;
import bots.helpers.PlayerManager;
import bots.helpers.ServerMusicManager;
import bots.listeners.SaitamaAudioListener;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import models.ServerCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import service.MessageBuilderService;

public class QueueCommand extends ServerCommand {


    private MessageBuilderService messageBuilderService = new MessageBuilderService();

    private final AudioPlayerManager playerManager = PlayerManager.getManager();

    private static final Logger logger = LogManager.getLogger(SaitamaAudioListener.class);

    public QueueCommand(){
        super("!queue");
    }

    @Override
    protected void runCommand(MessageCreateEvent event, Server server, ServerTextChannel channel, User user, String[] args) {

        logger.info("The ");
        System.out.println(" ");
        ServerMusicManager m = AudioManager.get(event.getServer().get().getId());
        AudioSource source = new LavaPlayerAudioSource(event.getApi(), m.player);

        //1. Set the AudioSource

        //2. Set the TrackScheduler

        //3. Get the list in order with

        
        //4. Set it pretty


    }
}