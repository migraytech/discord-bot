package bots.listeners.commands;

import service.MessageBuilderService;
import models.ServerCommand;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Arrays;

public class WatchAnimeCommand extends ServerCommand {


    public WatchAnimeCommand(){
        super("watch-anime");
    }

    private final MessageBuilderService messageBuilderService = new MessageBuilderService();
    private final String BASE_URL = "https://kissanime.com.ru/";
    private String link;
    @Override
    protected void runCommand(MessageCreateEvent event, Server server, ServerTextChannel channel, User user, String[] args) {
        if(event.getServer().isPresent()){
            try
            {
                String anime_name = args[1];
                link = BASE_URL+"Anime/"+anime_name;
                messageBuilderService.sendMessage(event.getMessageAuthor(),"Requested Anime","This anime is requested","Watch and check the link above. The anime that you requested: "+ anime_name.toUpperCase(),"https://i.pinimg.com/736x/19/21/f7/1921f7d346f27b7364a9b6b7a24072c2.jpg",link,channel);
                System.out.println("The anime that you requested "+ anime_name);
                System.out.println("Link of the anime: "+BASE_URL+"Anime/"+anime_name);

            }
            catch (Exception e){
                e.getStackTrace();
            }
        }
    }
}
