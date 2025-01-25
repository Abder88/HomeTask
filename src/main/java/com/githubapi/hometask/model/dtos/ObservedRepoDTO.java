package com.githubapi.hometask.model.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.githubapi.hometask.model.entities.ObservedRepo;
import com.githubapi.hometask.model.enums.ObservedRepoStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObservedRepoDTO {

  private Long id;
  private UUID uuid;
  private String url;
  private String owner;
  private String name;
  private int stars;
  private int openIssues;
  private String license;
  private ObservedRepoStatus status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;



}

