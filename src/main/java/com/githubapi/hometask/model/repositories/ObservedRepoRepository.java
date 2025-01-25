package com.githubapi.hometask.model.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.githubapi.hometask.model.entities.ObservedRepo;
import com.githubapi.hometask.model.enums.ObservedRepoStatus;
import com.githubapi.hometask.model.queryresults.ObservedRepoQueryResult;

@Repository
public interface ObservedRepoRepository extends JpaRepository<ObservedRepo, Long> {

  Optional<ObservedRepo> findByUuid(UUID id);

  // @formatter:off
  @Query(
      value =
          "SELECT or.* " +
              "FROM observed_repo or " +
              "WHERE " +
              "(:status IS NULL OR d.status = CAST(:status AS varchar)) " +
              "AND (:owner IS NULL OR :owner = '' OR LOWER(d.owner) LIKE LOWER(CONCAT('%', :owner, '%'))) " +
              "AND (:name IS NULL OR :name = '' OR LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
              "AND (:license IS NULL OR :license = '' OR LOWER(d.license) LIKE LOWER(CONCAT('%', :license, '%')))",
      countQuery =
          "SELECT COUNT(*) " +
              "FROM observed_repo d " +
              "WHERE " +
              "(:status IS NULL OR d.status = CAST(:status AS varchar)) " +
              "AND (:owner IS NULL OR :owner = '' OR LOWER(d.owner) LIKE LOWER(CONCAT('%', :owner, '%'))) " +
              "AND (:name IS NULL OR :name = '' OR LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
              "AND (:license IS NULL OR :license = '' OR LOWER(d.license) LIKE LOWER(CONCAT('%', :license, '%')))",
      nativeQuery = true
  )
  Page<ObservedRepoQueryResult> findAllByFilters(
      @Param("owner") String owner,
      @Param("name") String name,
      @Param("status") ObservedRepoStatus status,
      @Param("license") String license,
      Pageable pageable
  );


  // @formatter:off
  @Query(
      value =
          """
              SELECT or.*  
              FROM observed_repo or 
              WHERE  (:status IS NULL OR d.status = CAST(:status AS varchar))""",
      countQuery =
          """ 
              SELECT COUNT(*) 
              FROM observed_repo d 
              WHERE (:status IS NULL OR d.status = CAST(:status AS varchar)) """ ,
      nativeQuery = true
  )
  Page<ObservedRepoQueryResult> findAllActivePaginated(
      @Param("status") ObservedRepoStatus status,
      Pageable pageable
  );

  Page<ObservedRepo> findAllByOwnerContainingIgnoreCaseAndNameContainingIgnoreCaseAndStatusAndLicenseContainingIgnoreCase(
      String owner,
      String name,
      ObservedRepoStatus status,
      String license,
      Pageable pageable
  );
}
