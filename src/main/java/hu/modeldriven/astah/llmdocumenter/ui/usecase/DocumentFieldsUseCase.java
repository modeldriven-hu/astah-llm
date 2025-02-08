package hu.modeldriven.astah.llmdocumenter.ui.usecase;

import dev.langchain4j.model.github.GitHubModelsChatModel;
import hu.modeldriven.astah.core.AstahRepresentation;
import hu.modeldriven.astah.llmdocumenter.ui.event.DocumentFieldsRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

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
        var prompt = """
                I have a UML Class called %s. Generate a one sentence documentation for each field.
                Return the result in a json object, where every field is a key, and the documentation is the value.
                The fields will be listed in a format of name : type, separated by a comma. 
                The fields are the following: %s
                """.formatted(event.element().getName(), fieldList(event));

        var model = GitHubModelsChatModel.builder()
                .gitHubToken(System.getenv("ASTAH_GITHUB_TOKEN"))
                .modelName("gpt-4o-mini")
                .build();

        var response = model.chat(prompt);

        System.out.println(response);
    }

    private String fieldList(DocumentFieldsRequestedEvent event) {
        var result = new StringBuilder();

        for (var attribute : event.element().getAttributes()){
            result.append(attribute.getName()).append(" : ").append(attribute.getType().getName()).append(", ");
        }

        return result.toString();
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return List.of(DocumentFieldsRequestedEvent.class);
    }
}
