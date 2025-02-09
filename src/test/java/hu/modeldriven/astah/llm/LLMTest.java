package hu.modeldriven.astah.llm;

import hu.modeldriven.astah.llm.ui.prompt.DocumentGenerationPrompt;
import org.junit.Assert;
import org.junit.Test;

public class LLMTest {

    @Test
    public void testLLM(){
        var response = new DocumentGenerationPrompt("MyClass", "field1 : int, field2 : String").response();
        Assert.assertNotNull(response);
        Assert.assertEquals(2, response.size());
        Assert.assertTrue(response.containsKey("field1"));
        Assert.assertTrue(response.containsKey("field2"));
    }

}
