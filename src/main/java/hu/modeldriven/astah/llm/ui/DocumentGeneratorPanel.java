package hu.modeldriven.astah.llm.ui;

import com.change_vision.jude.api.inf.model.IClass;
import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.llm.ui.event.DocumentFieldsRequestedEvent;
import hu.modeldriven.astah.llm.ui.usecase.DocumentFieldsUseCase;
import hu.modeldriven.core.eventbus.EventBus;

import javax.swing.*;
import java.awt.*;

public class DocumentGeneratorPanel extends JPanel {

    private final EventBus eventBus;
    private final AstahRepresentation astah;

    public DocumentGeneratorPanel(EventBus eventBus, AstahRepresentation astah) {
        this.eventBus = eventBus;
        this.astah = astah;
        initUseCases();
        initPanel();
    }

    private void initUseCases() {
        this.eventBus.subscribe(new DocumentFieldsUseCase(eventBus, astah));
    }

    private void initPanel() {
        var generateButton = new JButton("Generate documentation");
        generateButton.addActionListener(e -> generateDocumentation());
        this.setLayout(new BorderLayout());
        this.add(generateButton, BorderLayout.SOUTH);
    }

    private void generateDocumentation() {
        var presentationNode = this.astah.selectedNodes().getFirst();
        if (presentationNode.getModel() instanceof IClass namedElement) {
            this.eventBus.publish(new DocumentFieldsRequestedEvent(namedElement));
        } else {
            JOptionPane.showMessageDialog(this, "Selected element is not a class", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

}
