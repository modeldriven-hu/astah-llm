package hu.modeldriven.astah.llm.ui.prompt;

import dev.langchain4j.model.github.GitHubModelsChatModel;

import java.util.HashMap;
import java.util.Map;

public class DocumentGenerationPrompt {

    private final String className;
    private final String fields;

    public DocumentGenerationPrompt(String className, String fields) {
        this.className = className;
        this.fields = fields;
    }

    public Map<String, String> response() {
        var prompt = """
                    I have a UML class named "%s".
                    For each field in this class, generate a simple one-sentence documentation. 
                
                    Return the result in standard Java properties file format, where:
                        - Each line is a key-value pair.
                        - The key is the field name.
                        - The value is the generated documentation.
                
                    Do not include anything else in the output—only the key-value pairs.
                
                    The fields are listed in the following format: name : type, separated by commas.
                    Fields: %s
                """.formatted(className, fields);

        var model = GitHubModelsChatModel.builder()
                .gitHubToken(System.getenv("ASTAH_GITHUB_TOKEN"))
                .modelName("gpt-4o-mini")
                .build();

        var result = model.chat(prompt);
        var response = new HashMap<String, String>();

        for (var line : result.split("\n")) {
            var parts = line.split("=", 2);
            if (parts.length == 2) {
                response.put(parts[0].trim(), parts[1].trim());
            }
        }

        return response;
    }

}
