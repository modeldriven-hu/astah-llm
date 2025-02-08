package hu.modeldriven.astah.llmdocumenter.ui;

import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.core.eventbus.EventBus;

import javax.swing.*;
import java.awt.*;

public class DocumentGeneratorDialog extends JDialog {

    private final EventBus eventBus;
    private final AstahRepresentation astah;

    public DocumentGeneratorDialog(EventBus eventBus, AstahRepresentation astah) {
        this.eventBus = eventBus;
        this.astah = astah;
        initDialog();
    }

    private void initDialog(){
        var panel = new DocumentGeneratorPanel(eventBus, astah);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(panel, BorderLayout.CENTER);
        this.pack();
    }

}
