package commands;

import org.javacord.api.Javacord;
import service.MessageBuilderService;
import service.ServerCommand;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class WatchAnimeCommand extends ServerCommand {


    public WatchAnimeCommand(){
        super("watch-anime");
    }

    private final MessageBuilderService messageBuilderService = new MessageBuilderService();
    private final String BASE_URL = "https://kissanime.com.ru/kissanime-home.html";
    @Override
    protected void runCommand(MessageCreateEvent event, Server server, ServerTextChannel channel, User user, String[] args) {

        if(event.getServer().isPresent()){

            try
            {
                String anime_name = args[1];
                messageBuilderService.sendMessage(event.getMessageAuthor(),"Requested Anime","This anime is requested ","Watch and check the link for the anime: "+ anime_name,"https://i.pinimg.com/736x/19/21/f7/1921f7d346f27b7364a9b6b7a24072c2.jpg"," ",channel);



            }
            catch (Exception e){
                e.getStackTrace();
            }

        }




    }
}
