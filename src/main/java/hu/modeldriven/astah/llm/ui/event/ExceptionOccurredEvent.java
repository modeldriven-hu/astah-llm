package hu.modeldriven.astah.llm.ui.event;

import hu.modeldriven.core.eventbus.Event;

public record ExceptionOccurredEvent(Exception exception) implements Event {
}
