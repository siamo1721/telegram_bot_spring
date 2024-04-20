package ru.tgbot.tgbot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.tgbot.tgbot.model.Joke;
import ru.tgbot.tgbot.service.JokeService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
@AutoConfigureMockMvc
@WebMvcTest(JokeController.class)
class JokeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JokeService jokeService;

    @InjectMocks
    private JokeController jokeController;

    @Test
    void getAllJokes() throws Exception {
        given(jokeService.getAllJokes()).willReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/Jokes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getJokeById() throws Exception {
        Long id = 1L;
        Joke joke = new Joke();
        given(jokeService.getJokesById(id)).willReturn(Optional.of(joke));

        mockMvc.perform(MockMvcRequestBuilders.get("/Jokes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void addNewJoke() throws Exception {
        Joke joke = new Joke();
        given(jokeService.addNewJoke(any())).willReturn(Optional.of(joke));

        mockMvc.perform(MockMvcRequestBuilders.post("/Jokes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(joke)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateJoke() throws Exception {
        Long id = 1L;
        Joke updatedJoke = new Joke();
        given(jokeService.updateJoke(id, updatedJoke)).willReturn(updatedJoke);

        mockMvc.perform(MockMvcRequestBuilders.put("/Jokes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedJoke)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteJoke() throws Exception {
        Long id = 1L;
        Joke joke = new Joke();
        given(jokeService.getJokesById(id)).willReturn(Optional.of(joke));
        given(jokeService.deleteJoke(joke)).willReturn(joke);

        mockMvc.perform(MockMvcRequestBuilders.delete("/Jokes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String asJsonString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}