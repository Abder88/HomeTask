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
          "SELECT id ,BIN_TO_UUID(uuid) uuid,url,license,name ,owner ,open_issues openIssues,stars,updated_at updatedAt,created_at createdAt " +
              "FROM observed_repo " +
              "WHERE " +
              "(:status IS NULL OR  status = :status) " +
              "AND (:owner IS NULL OR :owner = '' OR LOWER(owner) LIKE LOWER(CONCAT('%', :owner, '%'))) " +
              "AND (:name IS NULL OR :name = '' OR LOWER(name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
              "AND (:license IS NULL OR :license = '' OR LOWER(license) LIKE LOWER(CONCAT('%', :license, '%')))",
      countQuery =
          "SELECT COUNT(*) " +
              "FROM observed_repo " +
              "WHERE " +
              "(:status IS NULL OR status = :status) " +
              "AND (:owner IS NULL OR :owner = '' OR LOWER(owner) LIKE LOWER(CONCAT('%', :owner, '%'))) " +
              "AND (:name IS NULL OR :name = '' OR LOWER(name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
              "AND (:license IS NULL OR :license = '' OR LOWER(license) LIKE LOWER(CONCAT('%', :license, '%')))",
      nativeQuery = true
  )
  Page<ObservedRepoQueryResult> findAllByFilters( String owner,String name, String status,String license, Pageable pageable
  );


  // @formatter:off
  @Query(
      value =
          """
              SELECT owner,name 
              FROM observed_repo  
              WHERE  (:status IS NULL OR  status = CAST(:status AS varchar))""",
      countQuery =
          """ 
              SELECT COUNT(*) 
              FROM observed_repo  
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
