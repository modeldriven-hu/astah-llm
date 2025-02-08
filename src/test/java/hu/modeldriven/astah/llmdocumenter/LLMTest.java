package hu.modeldriven.astah.llmdocumenter;

import hu.modeldriven.astah.llmdocumenter.ui.DocumentGenerationPrompt;
import org.junit.Test;

public class LLMTest {

    @Test
    public void testLLM(){
        var response = new DocumentGenerationPrompt("MyClass", "field1 : int, field2 : String").response();
        System.out.println(response);
    }

}
