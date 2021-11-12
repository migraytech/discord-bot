package bots.helpers;

import bots.PlayerManager;
import bots.helpers.ServerMusicManager;

import java.util.HashMap;
import java.util.Map;

public class AudioManager {

    /**
     * Retrieves the server music manager dedicated for the server.
     *
     * @return a bots.helpers.ServerMusicManager.
     */

    private static final Map<Long, ServerMusicManager> managers = new HashMap<>();

    /**
     * Retrieves the server music manager dedicated for the server.
     * @param server the server's identification number.
     * @return a bots.helpers.ServerMusicManager.
     */
    public static ServerMusicManager get(long server) {
        // If it doesn't exist then we create one.
        if (!managers.containsKey(server)) {
            managers.put(server, new ServerMusicManager(PlayerManager.getManager()));
        }

        return managers.get(server);
    }

}