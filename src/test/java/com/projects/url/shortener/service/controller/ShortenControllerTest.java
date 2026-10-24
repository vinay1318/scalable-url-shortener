
package com.projects.url.shortener.service.controller;

import com.projects.url.shortener.service.exception.InvalidUrlException;
import com.projects.url.shortener.service.service.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ShortenController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ShortenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UrlService urlService;

    @Test
    void shouldReturn400WhenUrlIsEmpty() throws Exception {

        String requestBody = """
                {
                    "originalUrl": ""
                }
                """;

        mockMvc.perform(
                        post("/shorten")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isBadRequest());

        verifyNoInteractions(urlService);
    }


    @Test
    void shouldReturn400WhenUrlContainsOnlyWhitespace() throws Exception {

        String requestBody = """
            {
                "originalUrl": "   "
            }
            """;

        mockMvc.perform(
                        post("/shorten")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Original Url Cannot be Blank"));

        verifyNoInteractions(urlService);
    }


    @Test
    void shouldReturn400WhenOriginalUrlIsMissing() throws Exception {

        String requestBody = "{}";

        mockMvc.perform(
                        post("/shorten")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Original Url Cannot be Blank"));

        verifyNoInteractions(urlService);
    }

    @Test
    void shouldReturn200WhenUrlIsValid() throws Exception {

        String requestBody = """
            {
                "originalUrl": "https://google.com"
            }
            """;

        when(urlService.createShortUrl("https://google.com")).thenReturn("abc123");

        mockMvc.perform(
                        post("/shorten")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortCode")
                        .value("abc123"))
                .andExpect(jsonPath("$.shortUrl")
                        .value("http://localhost:8080/abc123"));

        verify(urlService).createShortUrl("https://google.com");


    }


    @Test
    void shouldReturn400WhenUrlIsInvalid() throws Exception {

        String requestBody = """
            {
                "originalUrl": "hello123"
            }
            """;

        when(urlService.createShortUrl("hello123"))
                .thenThrow(new InvalidUrlException(
                        "Please enter a valid url"));

        mockMvc.perform(
                        post("/shorten")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Please enter a valid url"));

        verify(urlService).createShortUrl("hello123");
    }


}
