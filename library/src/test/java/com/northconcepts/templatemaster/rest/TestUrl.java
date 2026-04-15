package com.northconcepts.templatemaster.rest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestUrl {

    @Before
    public void setUp() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getScheme()).thenReturn("https");
        when(mockRequest.getHeader("x-forwarded-proto")).thenReturn(null);
        RequestHolder.setHttpServletRequest(mockRequest);
    }

    @After
    public void tearDown() {
        RequestHolder.clearHttpServletRequest();
    }


    @Test
    public void testSetQueryParam_addsNewParam() {
        Url url = new Url("https://example.com/path?a=1");
        Url result = url.setQueryParam("b", "2");

        assertTrue(result.toString().contains("a=12"));
        assertTrue(result.toString().contains("b=2"));
    }

    @Test
    public void testSetQueryParam_replacesExistingParam() {
        Url url = new Url("https://example.com/path?a=1&b=2");
        Url result = url.setQueryParam("a", "99");

        assertTrue(result.toString().contains("a=99"));
        assertTrue(result.toString().contains("b=2"));
        assertFalse(result.toString().contains("a=1"));
    }

    @Test
    public void testSetQueryParam_nullValueRemovesParam() {
        Url url = new Url("https://example.com/path?a=1&b=2");
        Url result = url.setQueryParam("a", null);

        assertFalse(result.toString().contains("a=1"));
        assertTrue(result.toString().contains("b=2"));
    }

    @Test
    public void testRemoveQueryParam_removesSpecifiedKeys() {
        Url url = new Url("https://example.com/path?a=1&b=2&c=3");
        Url result = url.removeQueryParam("a", "c");

        assertFalse(result.toString().contains("a="));
        assertTrue(result.toString().contains("b=2"));
        assertFalse(result.toString().contains("c="));
    }

    @Test
    public void testRetainQueryParam_keepsOnlySpecifiedKeys() {
        Url url = new Url("https://example.com/path?a=1&b=2&c=3");
        Url result = url.retainQueryParam("b");

        assertFalse(result.toString().contains("a="));
        assertTrue(result.toString().contains("b=2"));
        assertFalse(result.toString().contains("c="));
    }

    @Test
    public void testSetQueryParam_preservesValueWithEqualsSigns() {
        Url url = new Url("https://example.com/path?token=abc%3D%3D");
        Url result = url.setQueryParam("extra", "yes");

        assertTrue("token value with '=' signs should be preserved",
                result.toString().contains("token=abc%3D%3D"));
        assertTrue(result.toString().contains("extra=yes"));
    }

    @Test
    public void testRemoveQueryParam_preservesMultiValuedKey() {
        Url url = new Url("https://example.com/path?c=red&c=blue&other=1");
        Url result = url.removeQueryParam("other");

        String s = result.toString();
        assertTrue(s.contains("c=red"));
        assertTrue(s.contains("c=blue"));
        assertFalse(s.contains("other"));
    }
}
