package com.githubapi.hometask.services;


import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.githubapi.hometask.exceptions.ResourceNotFoundException;
import com.githubapi.hometask.model.dtos.ObservedRepoCreateRequestDTO;
import com.githubapi.hometask.model.dtos.ObservedRepoDTO;
import com.githubapi.hometask.model.dtos.PageDTO;
import com.githubapi.hometask.model.entities.ObservedRepo;
import com.githubapi.hometask.model.enums.ObservedRepoStatus;
import com.githubapi.hometask.model.queryresults.ObservedRepoQueryResult;
import com.githubapi.hometask.model.repositories.ObservedRepoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ObservedRepoQueryService {

  private final ObservedRepoRepository observedRepoRepository;


  public ObservedRepo findById(final Long id) {
    return observedRepoRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(ObservedRepo.class, id));
  }

  public ObservedRepo getObservedRepoByUuid(UUID id) {
    return observedRepoRepository.findByUuid(id)
        .orElseThrow(() -> new ResourceNotFoundException(ObservedRepo.class, id));
  }

  public Page<ObservedRepo> searchObservedRepoEntities(
      final String owner, final String name, final ObservedRepoStatus status, final String license,
      final Pageable pageable) {
    return observedRepoRepository.findAllByOwnerContainingIgnoreCaseAndNameContainingIgnoreCaseAndStatusAndLicenseContainingIgnoreCase(
        owner, name, status, license, pageable);
  }

  public PageDTO<ObservedRepoDTO> searchObservedRepo(
      final String owner, final String name, final ObservedRepoStatus status, final String license,
      final Pageable pageable) {
    final Page<ObservedRepoQueryResult> qrPage = observedRepoRepository.findAllByFilters(owner,
        name, status, license, pageable);
    List<ObservedRepoDTO> observedRepoDTOs = qrPage.getContent().stream()
        .map(ObservedRepoQueryResult::toDTO).toList();
    return PageDTO.toDTO(qrPage, observedRepoDTOs);
  }

  public Page<ObservedRepoQueryResult> searchObservedRepo(final ObservedRepoStatus status,
      final Pageable pageable) {
    return observedRepoRepository.findAllActivePaginated(status, pageable);
  }
}


