package com.githubapi.hometask;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.githubapi.hometask.model.dtos.ObservedRepoCreateRequestDTO;
import com.githubapi.hometask.model.dtos.ObservedRepoDTO;
import com.githubapi.hometask.model.dtos.PageDTO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.MethodName.class)
@Testcontainers
@Slf4j
class GithubControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private static Long toDeleteId;

  @Container
  private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0.32")
      .withDatabaseName("secfix")
      .withUsername("root")
      .withPassword("aitenders");

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
    registry.add("spring.datasource.username", mysqlContainer::getUsername);
    registry.add("spring.datasource.password", mysqlContainer::getPassword);
  }


  @BeforeAll
  static void beforeAllTests() {
    log.info("***** Starting GithubControllerTest suite *****"); 
  }

  @AfterAll
  static void afterAllTests() {
    log.info("***** Finished GithubControllerTest suite *****"); 
  }
  @Test
  public void a1_testGreetEndpoint() throws Exception {
    log.info("Running test a1_testGreetEndpoint()..."); 

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/observed-repos/up"))
        .andExpect(MockMvcResultMatchers.status().isOk());

    log.info("Completed test a1_testGreetEndpoint()."); 
  }

  @Test
  @DisplayName("POST /api/v1/observed-repos - Create ObservedRepo")
  void a2_createObservedRepo_shouldReturnCreated() throws Exception {
    log.info("Running test a2_createObservedRepo_shouldReturnCreated()..."); 

    // Given
    ObservedRepoCreateRequestDTO requestDTO = new ObservedRepoCreateRequestDTO();
    requestDTO.setOwner("Abder88");
    requestDTO.setName("HomeTask");
    requestDTO.setUrl("github.com.1");
    requestDTO.setLicense("MIT");

    mockMvc.perform(
        post("/api/v1/observed-repos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDTO))
    ).andExpect(status().isCreated());

    log.info("Completed test a2_createObservedRepo_shouldReturnCreated()."); 
  }

  @Test
  @DisplayName("POST /api/v1/observed-repos - Create ObservedRepo")
  void a3_createSecondObservedRepo_shouldReturnCreated() throws Exception {
    log.info("Running test a3_createSecondObservedRepo_shouldReturnCreated()..."); 

    // Given
    ObservedRepoCreateRequestDTO requestDTO = new ObservedRepoCreateRequestDTO();
    requestDTO.setOwner("Abder88");
    requestDTO.setName("HomeTask");
    requestDTO.setUrl("github.com.2");
    requestDTO.setLicense("MIT");

    mockMvc.perform(
        post("/api/v1/observed-repos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDTO))
    ).andExpect(status().isCreated());

    log.info("Completed test a3_createSecondObservedRepo_shouldReturnCreated()."); 
  }

  @Test
  @DisplayName("POST /api/v1/observed-repos - Create ObservedRepo")
  void a4_createThirdObservedRepo_shouldReturnCreated() throws Exception {
    log.info("Running test a4_createThirdObservedRepo_shouldReturnCreated()..."); 

    // Given
    ObservedRepoCreateRequestDTO requestDTO = new ObservedRepoCreateRequestDTO();
    requestDTO.setOwner("Abder88");
    requestDTO.setName("HomeTask");
    requestDTO.setUrl("github.com.3");
    requestDTO.setLicense("MIT");

    mockMvc.perform(
        post("/api/v1/observed-repos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDTO))
    ).andExpect(status().isCreated());

    log.info("Completed test a4_createThirdObservedRepo_shouldReturnCreated()."); 
  }

  @Test
  @DisplayName("Get /api/v1/observed-repos - search ObservedRepo")
  void a5_searchObservedRepo_paginatedList() throws Exception {
    log.info("Running test a5_searchObservedRepo_paginatedList()..."); 

    ResultActions result = mockMvc.perform(
        get("/api/v1/observed-repos")
            .contentType(MediaType.APPLICATION_JSON)
            .param("owner", "ab")
            .param("name", "task")
            .param("status", "ACTIVE")
            .param("license", "mi")
    ).andExpect(status().isOk());

    result.andExpect(jsonPath("$.results").isArray());
    result.andExpect(jsonPath("$.previous").doesNotExist());

    try {
      // Map JSON string to PageDTO<ObservedRepoDTO>
      PageDTO<ObservedRepoDTO> pageDTO = objectMapper.readValue(
          result.andReturn().getResponse().getContentAsString(),
          new TypeReference<PageDTO<ObservedRepoDTO>>() {}
      );

      log.info("Current PageDTO: {}", pageDTO); 
      String nextPage = pageDTO.getNext();
      final Optional<ObservedRepoDTO> toDelete = pageDTO.getResults().stream().findFirst();
      Assertions.assertTrue(toDelete.isPresent());
      toDeleteId = toDelete.get().getId();
      log.info("ObservedRepo id to Delete : {}", toDeleteId);
      // Next Page
      result = mockMvc.perform(
          get("/api/v1/observed-repos")
              .contentType(MediaType.APPLICATION_JSON)
              .param("owner", "ab")
              .param("name", "task")
              .param("status", "ACTIVE")
              .param("license", "mi")
              .param("nextPage", nextPage)
      ).andExpect(status().isOk());

      result.andExpect(jsonPath("$.results").isArray());
      result.andExpect(jsonPath("$.next").doesNotExist());

    } catch (Exception e) {
      log.error("Error during pagination test: ", e); 
      e.printStackTrace();
    }

    log.info("Completed test a5_searchObservedRepo_paginatedList()."); 
  }

  @Test
  @DisplayName("delete /api/v1/observed-repos/{id} - soft delete ObservedRepo")
  void a6_getObservedRepo_shouldReturnObservedRepoDTO() throws Exception {
    log.info("Running test a6_getObservedRepo_shouldReturnObservedRepoDTO()..."); 

    mockMvc.perform(
        delete("/api/v1/observed-repos/" + toDeleteId)
            .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(status().isOk());

    log.info("Completed test a6_getObservedRepo_shouldReturnObservedRepoDTO()."); 
  }

  @Test
  @DisplayName("get /api/v1/observed-repos/{id} - Create ObservedRepo")
  void a7_getObservedRepo_shouldReturnObservedRepoDTO() throws Exception {
    log.info("Running test a7_getObservedRepo_shouldReturnObservedRepoDTO()..."); 

    mockMvc.perform(
        get("/api/v1/observed-repos/" + toDeleteId)
            .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(status().isOk());

    log.info("Completed test a7_getObservedRepo_shouldReturnObservedRepoDTO()."); 
  }
}
