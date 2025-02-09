package hu.modeldriven.astah.llm.plugin;


import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate;
import com.change_vision.jude.api.inf.ui.IWindow;
import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.llm.ui.event.DocumentFieldsRequestedEvent;
import hu.modeldriven.astah.llm.ui.usecase.DisplayExceptionUseCase;
import hu.modeldriven.astah.llm.ui.usecase.DocumentFieldsUseCase;
import hu.modeldriven.core.eventbus.EventBus;

import javax.swing.*;

public class GenerateDocumentAction implements IPluginActionDelegate {

    public Object run(IWindow window) throws UnExpectedException {
        try {
            var astah = new AstahRepresentation();

            if (!astah.hasProject()) {
                JOptionPane.showMessageDialog(window.getParent(), "Project is not opened. Please open the project or create new project.", "Warning", JOptionPane.WARNING_MESSAGE);
                return null;
            }

            if (astah.selectedNodes().isEmpty()) {
                JOptionPane.showMessageDialog(window.getParent(), "Please select an element on the diagram.", "Warning", JOptionPane.WARNING_MESSAGE);
                return null;
            }

            if (astah.selectedNodes().size() > 1) {
                JOptionPane.showMessageDialog(window.getParent(), "Please select only one element on the diagram.", "Warning", JOptionPane.WARNING_MESSAGE);
                return null;
            }

            if (System.getenv("ASTAH_GITHUB_TOKEN") == null) {
                JOptionPane.showMessageDialog(window.getParent(), "ASTAH_GITHUB_TOKEN environment variable is not set.", "Warning", JOptionPane.WARNING_MESSAGE);
                return null;
            }

            if (astah.selectedNodes().getFirst().getModel() instanceof IClass namedElement) {
                var eventBus = new EventBus();
                eventBus.subscribe(new DocumentFieldsUseCase(eventBus, astah));
                eventBus.subscribe(new DisplayExceptionUseCase());
                eventBus.publish(new DocumentFieldsRequestedEvent(namedElement));
            } else {
                JOptionPane.showMessageDialog(window.getParent(), "Selected element is not a class", "Warning", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(window.getParent(), "Unexpected error has occurred.", "Alert", JOptionPane.ERROR_MESSAGE);
            throw new UnExpectedException();
        }

        return null;
    }

}
