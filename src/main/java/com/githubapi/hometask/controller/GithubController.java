package com.githubapi.hometask.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.githubapi.hometask.model.dtos.ObservedRepoCreateRequestDTO;
import com.githubapi.hometask.model.dtos.ObservedRepoDTO;
import com.githubapi.hometask.model.dtos.PageDTO;
import com.githubapi.hometask.model.enums.ObservedRepoStatus;
import com.githubapi.hometask.services.ObservedRepoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/observed-repos")
@Tag(name = "  First Test Github Controller")
public class GithubController {

  private final ObservedRepoService service;

  @GetMapping("/up")
  @Operation(summary = "Check the API status", description = "Returns an OK status to indicate the API is up and running.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "API is up and running")
  })
  public ResponseEntity up() {
return ResponseEntity.status(HttpStatus.OK).body(null);
  }

  @PostMapping
  @Operation(summary = "Create an observed repository", description = "Creates a new observed repository.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Repository created successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input")
  })
  public ResponseEntity<ObservedRepoDTO> createObservedRepo(
      @RequestBody @Valid ObservedRepoCreateRequestDTO request) {
    ObservedRepoDTO saved = service.createObservedRepo(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(null);
  }

  @Operation(summary = "Delete an observed repository", description = "Deletes an observed repository by its ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Repository deleted successfully"),
      @ApiResponse(responseCode = "404", description = "Repository not found")
  })
  @DeleteMapping("/{id}")
  public void deleteObservedRepo(@PathVariable("id") @Min(1) @NonNull final Long id) {
    service.deleteObservedRepo(id);
  }

  @Operation(summary = "Get an observed repository", description = "Fetches the details of an observed repository by its ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Repository found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ObservedRepoDTO.class))),
      @ApiResponse(responseCode = "404", description = "Repository not found")
  })
  @GetMapping("/{id}")
  public ObservedRepoDTO getObservedRepo(@PathVariable("id") @Min(1) @NonNull final Long id) {
    return service.getObservedRepo(id);
  }

  @Operation(summary = "Get a list of observed repositories", description = "Retrieves a paginated list of observed repositories, with optional filters.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Repositories fetched successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid query parameters")
  })
  @GetMapping
  public PageDTO<ObservedRepoDTO> getObservedRepos(
      @RequestParam(required = false, defaultValue = "") String owner,
      @RequestParam(required = false, defaultValue = "") String name,
      @RequestParam(required = false, defaultValue = "ACTIVE") ObservedRepoStatus status,
      @RequestParam(required = false, defaultValue = "") String license,
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
    return service.searchObservedRepo(owner, name, status, license, pageable);
  }

}

