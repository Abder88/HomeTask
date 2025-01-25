package com.githubapi.hometask.services;


import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.githubapi.hometask.exceptions.ResourceNotFoundException;
import com.githubapi.hometask.model.dtos.ObservedRepoCreateRequestDTO;
import com.githubapi.hometask.model.dtos.ObservedRepoDTO;
import com.githubapi.hometask.model.entities.ObservedRepo;
import com.githubapi.hometask.model.enums.ObservedRepoStatus;
import com.githubapi.hometask.model.repositories.ObservedRepoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ObservedRepoCommandService {

  private final ObservedRepoRepository observedRepoRepository;


  public ObservedRepo save(ObservedRepo observedRepo) {
    return observedRepoRepository.save(observedRepo);
  }

  public void saveAll(final Collection<ObservedRepo> collection) {
    observedRepoRepository.saveAll(collection);
  }
}


