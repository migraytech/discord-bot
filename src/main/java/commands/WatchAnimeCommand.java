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
    private String anime_name = "";

    @Override
    protected void runCommand(MessageCreateEvent event, Server server, ServerTextChannel channel, User user, String[] args) {




    }
}
