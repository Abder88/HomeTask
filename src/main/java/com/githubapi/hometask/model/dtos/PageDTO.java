package com.githubapi.hometask.model.dtos;


import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class PageDTO<T> {

  List<T> results;
  String next;
  String previous;
  private PageDTO() {}

  public static <T> PageDTO<T> toDTO(int actualPage, final Page<?> page, final List<T> contents, String previous, String next) {
    final PageDTO<T> dto = new PageDTO<>();
    dto.setResults(contents);
    dto.setPrevious(previous);
    dto.setNext(next);
    return dto;
  }

  public static <T> PageDTO<T> emptyPage() {
    final PageDTO<T> dto = new PageDTO<>();
    dto.setResults(Collections.emptyList());
    return dto;
  }
}
