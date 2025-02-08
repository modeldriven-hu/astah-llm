package hu.modeldriven.astah.llmdocumenter.ui.usecase;

import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.model.IClass;
import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.core.transaction.AstahTransaction;
import hu.modeldriven.astah.core.transaction.TransactionFailedException;
import hu.modeldriven.astah.llmdocumenter.ui.DocumentGenerationPrompt;
import hu.modeldriven.astah.llmdocumenter.ui.event.DocumentFieldsRequestedEvent;
import hu.modeldriven.astah.llmdocumenter.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import javax.swing.*;
import java.util.List;

public class DocumentFieldsUseCase implements EventHandler<DocumentFieldsRequestedEvent> {

    private final EventBus eventBus;
    private final AstahRepresentation astah;

    public DocumentFieldsUseCase(EventBus eventBus, AstahRepresentation astah) {
        this.eventBus = eventBus;
        this.astah = astah;
    }

    @Override
    public void handleEvent(DocumentFieldsRequestedEvent event) {

        try {
            var prompt = new DocumentGenerationPrompt(
                    event.element().getName(),
                    attributesAsString(event.element()));

            var documentation = prompt.response();

            AstahTransaction transaction = new AstahTransaction();

            transaction.execute(() -> {
                for (var attribute : event.element().getAttributes()) {

                    if (documentation.containsKey(attribute.getName())) {
                        try {
                            attribute.setDefinition(documentation.get(attribute.getName()));
                        } catch (InvalidEditingException e) {
                            eventBus.publish(new ExceptionOccurredEvent(e));
                            return;
                        }
                    }

                }
            });

            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, "Documentation generated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            });

        } catch (TransactionFailedException e){
            eventBus.publish(new ExceptionOccurredEvent(e));
        }
    }

    private String attributesAsString(IClass element) {
        var result = new StringBuilder();

        for (var attribute : element.getAttributes()){
            result.append(attribute.getName()).append(" : ").append(attribute.getType().getName()).append(", ");
        }

        return result.toString();
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return List.of(DocumentFieldsRequestedEvent.class);
    }
}
