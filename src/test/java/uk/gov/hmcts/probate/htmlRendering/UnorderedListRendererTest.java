package uk.gov.hmcts.probate.htmlRendering;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class UnorderedListRendererTest {
    private UnorderedListRenderer ulRenderer;

    @Before
    public void setup() throws Exception {
        ulRenderer = new UnorderedListRenderer();
    }

    @Test
    public void shouldRenderUnorderedListCorrectly() {
        List<String> testListItems =
                Arrays.asList(new String[]{"Test list item 1", "Test list item 2"});
        String expectedValue = "<ul class=\"govuk-list govuk-list--bullet\">\n<li>Test list item 1</li>\n<li>Test list item 2</li>\n</ul>\n";
        String result = ulRenderer.render(testListItems);
        assertTrue(result.equals(expectedValue));
    }
}
