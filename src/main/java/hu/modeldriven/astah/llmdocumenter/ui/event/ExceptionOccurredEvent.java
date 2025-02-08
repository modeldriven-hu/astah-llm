package hu.modeldriven.astah.llmdocumenter.ui.event;

import hu.modeldriven.core.eventbus.Event;

public record ExceptionOccurredEvent(Exception exception) implements Event {
}
