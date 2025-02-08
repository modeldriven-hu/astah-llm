package hu.modeldriven.core.eventbus;

import java.util.List;

public interface EventHandler<T extends Event> {
    void handleEvent(T event);

    List<Class<? extends Event>> subscribedEvents();
}
