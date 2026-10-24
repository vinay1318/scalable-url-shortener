
package com.projects.url.shortener.service.controller;

import com.projects.url.shortener.service.service.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.verifyNoInteractions;
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




}
