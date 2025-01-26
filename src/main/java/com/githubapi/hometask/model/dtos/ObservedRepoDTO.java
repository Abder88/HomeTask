package com.githubapi.hometask.model.dtos;

import java.time.LocalDateTime;

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
  private String uuid;
  private String url;
  private String owner;
  private String name;
  private int stars;
  private int openIssues;
  private String license;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;



}

