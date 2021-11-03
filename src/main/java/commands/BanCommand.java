package commands;

import service.MessageBuilderService;
import service.ServerCommand;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class BanCommand extends ServerCommand {


    private MessageBuilderService messageBuilderService = new MessageBuilderService();
    public BanCommand() {
        super("ban");
    }

    @Override
    protected void runCommand(MessageCreateEvent event, Server server, ServerTextChannel channel, User user, String[] args) {


        if(event.getServer().isPresent()){
            try {

                if(!event.getMessageAuthor().isRegularUser()) {
                    event.getChannel().sendMessage("You have no permissions to do that");
                }
                //split the Message
                System.out.println("Start to ban the user!");
                String username = args[1];
                //User banUser = event.getServer().get().getMembers().stream().filter(user1 -> user1.getName().equals(username)).findFirst().get();
                //event.getServer().get().banUser(banUser);
                event.getChannel().sendMessage(new File("C:/Users/Mignon/Pictures/pic4.jpg"));
                //BufferedImage image = ImageIO.read(getClass().getResource("/resources/icon.gif"));
                //event.getChannel().sendMessage(new File("C:/Users/Mignon/Pictures/saitama-bot-response-gifs"));

            }
            catch (Exception e){
                e.getStackTrace();
            }

        }

    }


    private void banUserFromServer(User user,MessageCreateEvent event){


        // ban user from server

        // send message that user is ban

    }
}
