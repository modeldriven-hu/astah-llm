package hu.modeldriven.astah.llmdocumenter.ui.event;

import com.change_vision.jude.api.inf.model.IClass;
import hu.modeldriven.core.eventbus.Event;

public record DocumentFieldsRequestedEvent(IClass element) implements Event {
}
