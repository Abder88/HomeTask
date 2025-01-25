package com.githubapi.hometask.model.dtos;


import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class PageDTO<T> {

  List<T> results;
  int totalPages;
  long totalElements;
  int pageNumber;
  boolean hasMore;

  private PageDTO() {}

  @JsonIgnore
  public static <T> PageDTO<T> toDTO(final Page<T> page) {
    final PageDTO<T> dto = new PageDTO<>();
    dto.setHasMore(page.hasNext());
    dto.setTotalPages(page.getTotalPages());
    dto.setPageNumber(page.getNumber());
    dto.setTotalElements(page.getTotalElements());
    return dto;
  }

  @JsonIgnore
  public static <T> PageDTO<T> toDTO(final Page<?> page, final List<T> contents) {
    final PageDTO<T> dto = new PageDTO<>();
    dto.setResults(contents);
    dto.setHasMore(page.hasNext());
    dto.setTotalPages(page.getTotalPages());
    dto.setPageNumber(page.getNumber());
    dto.setTotalElements(page.getTotalElements());
    return dto;
  }

  @JsonIgnore
  public static <T> PageDTO<T> emptyPage() {
    final PageDTO<T> dto = new PageDTO<>();
    dto.setResults(Collections.emptyList());
    dto.setHasMore(false);
    dto.setTotalPages(0);
    dto.setPageNumber(0);
    dto.setTotalElements(0);
    return dto;
  }
}
