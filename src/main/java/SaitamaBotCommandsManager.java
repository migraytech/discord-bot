import org.javacord.api.util.event.ListenerManager;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class SaitamaBotCommandsManager implements ListenerManager {

    @Override
    public boolean isGlobalListener() {
        return false;
    }

    @Override
    public Class getListenerClass() {
        return null;
    }

    @Override
    public Object getListener() {
        return null;
    }

    @Override
    public Optional<Class<?>> getAssignedObjectClass() {
        return Optional.empty();
    }

    @Override
    public Optional<Long> getAssignedObjectId() {
        return Optional.empty();
    }

    @Override
    public ListenerManager remove() {
        return null;
    }

    @Override
    public ListenerManager removeAfter(long l, TimeUnit timeUnit) {
        return null;
    }

    @Override
    public void addRemoveHandler(Runnable runnable) {

    }
}
