package com.northconcepts.templatemaster.content;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestContentJsonSerialization {

    @Test
    public void testJacksonSerialization_doesNotTraverseParentChain() throws Exception {
        Content page = new Content();
        page.add("body", new Content());

        String json = new ObjectMapper().writeValueAsString(page);

        assertFalse(json.contains("\"parent\""));
    }
}
