package com.githubapi.hometask;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.githubapi.hometask.controller.GithubController;
import com.githubapi.hometask.model.dtos.ObservedRepoCreateRequestDTO;
import com.githubapi.hometask.model.dtos.ObservedRepoDTO;
import com.githubapi.hometask.model.enums.ObservedRepoStatus;
import com.githubapi.hometask.model.repositories.ObservedRepoRepository;
import com.githubapi.hometask.services.ObservedRepoService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GithubControllerTest {

  @Autowired
  private ObservedRepoService service;
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testGreetEndpoint() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/observed-repos/up"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @DisplayName("POST /api/v1/observed-repos - Create ObservedRepo")
  void createObservedRepo_shouldReturnCreated() throws Exception {
    // Given
    ObservedRepoCreateRequestDTO requestDTO = new ObservedRepoCreateRequestDTO();
    requestDTO.setOwner("Abder88");
    requestDTO.setName("test");
    requestDTO.setUrl("github.com.dd");
    requestDTO.setLicense("MIT");

    final ResultActions result = mockMvc.perform(
            post("/api/v1/observed-repos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
        .andExpect(status().isCreated());
  }
}
