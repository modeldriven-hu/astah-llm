package hu.modeldriven.astah.llm.ui.usecase;

import hu.modeldriven.astah.llm.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventHandler;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;

public class DisplayExceptionUseCase implements EventHandler<ExceptionOccurredEvent> {

    @Override
    public void handleEvent(ExceptionOccurredEvent exceptionOccurredEvent) {

        SwingUtilities.invokeLater(() -> {

            StringWriter stringWriter = new StringWriter();
            exceptionOccurredEvent.exception().printStackTrace(new PrintWriter(stringWriter));

            JTextArea text = new JTextArea();
            text.setEditable(false);
            text.setText(stringWriter.toString());
            text.setCaretPosition(0);

            JScrollPane scrollPane = new JScrollPane(text);
            scrollPane.setPreferredSize(new Dimension(400, 400));

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.add(scrollPane, BorderLayout.CENTER);

            JOptionPane.showMessageDialog(null, panel, "Exception occurred!", JOptionPane.ERROR_MESSAGE);

        });
    }


    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(ExceptionOccurredEvent.class);
    }

}
