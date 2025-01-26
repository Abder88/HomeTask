package com.githubapi.hometask.services;


import java.util.Collection;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.githubapi.hometask.model.dtos.ObservedRepoCreateRequestDTO;
import com.githubapi.hometask.model.dtos.ObservedRepoDTO;
import com.githubapi.hometask.model.dtos.PageDTO;
import com.githubapi.hometask.model.entities.ObservedRepo;
import com.githubapi.hometask.model.enums.ObservedRepoStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ObservedRepoService {

  private final ObservedRepoQueryService queryService;
  private final ObservedRepoCommandService commandService;
  private final ModelMapper mapper;


  public ObservedRepoDTO createObservedRepo(ObservedRepoCreateRequestDTO request) {
    ObservedRepo repo = new ObservedRepo();
    repo.setUrl(request.getUrl());
    repo.setOwner(request.getOwner());
    repo.setName(request.getName());
    repo.setStatus(ObservedRepoStatus.ACTIVE);
    repo.setLicense(request.getLicense());
    repo.setUuid(UUID.randomUUID());
    repo = commandService.save(repo);
    return mapper.map(repo, ObservedRepoDTO.class);

  }

  public ObservedRepoDTO getObservedRepo(Long id) {
    ObservedRepo repo = queryService.findById(id);
    return mapper.map(repo, ObservedRepoDTO.class);
  }

  public void deleteObservedRepo(Long id) {
    ObservedRepo existing = queryService.findById(id);
    existing.setStatus(ObservedRepoStatus.DELETED);
    commandService.save(existing);
  }

  public void saveAll(Collection<ObservedRepo> collection) {
    commandService.saveAll(collection);
  }

  public PageDTO<ObservedRepoDTO> searchObservedRepo(String owner, String name,
      ObservedRepoStatus status, String license, Pageable pageable) throws Exception {
    return queryService.searchObservedRepo(owner, name, status, license, pageable);
  }

  public Page<ObservedRepo> searchRepos(String owner, String name, ObservedRepoStatus status,
      String license, Pageable pageable) {
    return queryService.searchObservedRepoEntities(owner, name, status, license, pageable);
  }
}


