package com.githubapi.hometask.model.queryresults;

import java.time.LocalDateTime;
import java.util.UUID;

import com.githubapi.hometask.model.dtos.ObservedRepoDTO;
import com.githubapi.hometask.model.enums.ObservedRepoStatus;

public interface ObservedRepoQueryResult {
  
    Long getId();
   UUID getUuid();
   String getUrl();
   String getOwner();
   String getName();
   Integer getStars();
  Integer getOpenIssues();
   String getLicense();
   ObservedRepoStatus getStatus();
   LocalDateTime getCreatedAt();
   LocalDateTime getUpdatedAt();

  default ObservedRepoDTO toDTO() {
    return ObservedRepoDTO.builder()
        .id(getId())
        .uuid(getUuid())
        .url(getUrl())
        .owner(getOwner())
        .stars(getStars())
        .openIssues(getOpenIssues())
        .license(getLicense())
        .status(getStatus())
        .createdAt(getCreatedAt())
        .updatedAt(getUpdatedAt())
        .build();
  }
}
