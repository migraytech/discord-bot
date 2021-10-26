package commands;

import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class CleanCommand extends ServerCommand {

    protected CleanCommand() {
        super(" ");

    }

    @Override
    protected void runCommand(MessageCreateEvent event, Server server, ServerTextChannel channel, User user, String[] args) {

    }
}
