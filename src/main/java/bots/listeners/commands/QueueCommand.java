package bots.listeners.commands;

import bots.helpers.AudioManager;
import bots.helpers.PlayerManager;
import bots.helpers.ServerMusicManager;

import bots.listeners.SaitamaAudioListener;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import models.ServerCommand;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.javacord.api.entity.channel.ServerTextChannel;


import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import service.MessageBuilderService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class QueueCommand extends ServerCommand {


    private MessageBuilderService messageBuilderService = new MessageBuilderService();

    private final AudioPlayerManager playerManager = PlayerManager.getManager();

    private static final Logger logger = LogManager.getLogger(SaitamaAudioListener.class);

    public QueueCommand(){
        super("queue");
    }

    @Override
    protected void runCommand(MessageCreateEvent event, Server server, ServerTextChannel channel, User user, String[] args) {

        if(event.getServer().isPresent())
        {
            try {

                logger.info("Get the list of of the requested tracks.");
                System.out.println("Get the list of of the requested tracks.");

                ServerMusicManager m = AudioManager.get(event.getServer().get().getId());

                //2. Set the TrackScheduler
                BlockingQueue<AudioTrack> trackList = m.scheduler.getTheQueueOfTheTracks();

                //Set the track-names
                List<String> trackNames = setTrackNames(trackList);

                //3. Get the list in order with
                messageBuilderService.sendMessage(event.getMessageAuthor(),"Queue track-list","track list requested","track list requested","https://i1.sndcdn.com/avatars-000658993130-ud4lgx-t500x500.jpg",null,event.getChannel());

                int i = 1;
                for (String track_name: trackNames) {
                    event.getChannel().sendMessage(i+". "+track_name);
                    i++;
                }
            }
            catch (Exception e)
            {
                throw e;
            }
        }
    }

    private List<String> setTrackNames (BlockingQueue<AudioTrack> tracks){
        try {
            List<String> trackNames =  new ArrayList<>();
            for (AudioTrack track:tracks) {
                trackNames.add(track.getInfo().title);
            }
            return trackNames;
        }
        catch (NullPointerException e){
            throw e;
        }

    }

}
