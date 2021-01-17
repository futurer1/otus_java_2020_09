package ru.otus.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.otus.domain.Client;
import ru.otus.services.ClientService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ClientRestControllerStandaloneTest {

    private MockMvc mvc;

    @Mock
    private ClientService clientService;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new ClientRestController(clientService)).build();
    }

    @Test
    void getUserById() throws Exception {
        Client expectedClient = new Client(1, "Vasya");
        Gson gson = new GsonBuilder().create();
        given(clientService.findById(1L)).willReturn(expectedClient);
        mvc.perform(get("/api/client/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string(gson.toJson(expectedClient)));
    }
}