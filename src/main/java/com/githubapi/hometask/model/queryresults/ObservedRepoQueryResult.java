package com.githubapi.hometask.model.queryresults;

import java.time.LocalDateTime;

import com.githubapi.hometask.model.dtos.ObservedRepoDTO;

public interface ObservedRepoQueryResult {
  
    Long getId();
   String getUuid();
   String getUrl();
   String getOwner();
   String getName();
   Integer getStars();
  Integer getOpenIssues();
   String getLicense();
   String getStatus();
   LocalDateTime getCreatedAt();
   LocalDateTime getUpdatedAt();

  default ObservedRepoDTO toDTO() {
    return ObservedRepoDTO.builder()
        .id(getId())
        .uuid(getUuid())
        .url(getUrl())
        .name(getName())
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
