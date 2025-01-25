package com.githubapi.hometask.model.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObservedRepoCreateRequestDTO {

  @NotNull
  @Size(max = 1000)
  private String url;

  @NotNull
  @Size(max = 1000)
  private String owner;

  @NotNull
  @Size(max = 1000)
  private String name;

  @Size(max = 100)
  private String license;
}

