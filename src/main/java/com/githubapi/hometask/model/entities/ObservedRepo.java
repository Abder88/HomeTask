package com.githubapi.hometask.model.entities;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.githubapi.hometask.model.enums.ObservedRepoStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true,callSuper=false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ObservedRepo extends AuditingEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  @EqualsAndHashCode.Include
  Long id;

  @EqualsAndHashCode.Include
  @Column(columnDefinition = "BINARY(16)")
  private UUID uuid;

  @Column(nullable = false)
  private String url;

  @Column(nullable = false, length = 1000)
  private String owner;

  @Column(nullable = false, length = 1000)
  private String name;

  private int stars;
  private int openIssues;

  @Column(length = 100)
  private String license;

  @Column(nullable = false, columnDefinition = "ENUM('ACTIVE', 'DELETED', 'INVALID')")
  @Enumerated(EnumType.STRING)
  private ObservedRepoStatus status;

}
