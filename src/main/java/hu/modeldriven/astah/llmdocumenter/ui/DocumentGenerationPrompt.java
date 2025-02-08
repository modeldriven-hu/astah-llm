package hu.modeldriven.astah.llmdocumenter.ui;

import dev.langchain4j.model.github.GitHubModelsChatModel;

import java.util.HashMap;
import java.util.Map;

public class DocumentGenerationPrompt {

    private final String className;
    private final String fields;

    public DocumentGenerationPrompt(String className, String fields){
        this.className = className;
        this.fields = fields;
    }

    public Map<String, String> response(){
        var prompt = """
                I have a UML Class called %s. Generate a one, simple sentence documentation for each field.
                Return the result as a standard java properties file format where every line is a key-value pair.
                The key shall be the field name, and the value shall be the documentation.
                Do not add anything else to the result, just the key-value pairs.
                The fields for the input will be listed in a format of name : type, separated by a comma. 
                The fields are the following: %s
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
