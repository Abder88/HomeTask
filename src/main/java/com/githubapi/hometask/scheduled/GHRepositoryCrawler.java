package com.githubapi.hometask.scheduled;


import java.util.ArrayList;
import java.util.List;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.githubapi.hometask.model.entities.ObservedRepo;
import com.githubapi.hometask.model.enums.ObservedRepoStatus;
import com.githubapi.hometask.services.ObservedRepoService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class GHRepositoryCrawler {

  @Value("${github.token}")
  private String token;

  private GitHub github;

  @Autowired
  private final ObservedRepoService observedRepoService;

  @PostConstruct
  public void init() {
    try {
      this.github = new GitHubBuilder().withOAuthToken(token).build();

      if (this.github.isCredentialValid()) {
        log.info("Successfully authenticated to GitHub.");
      } else {
        log.error("GitHub authentication failed.");
      }
    } catch (Exception e) {
      log.error("Failed to initialize GitHub client: {}", e.getMessage());
    }
  }

  @Scheduled(fixedRate = 5 * 60 * 1000)
  public void fetchRepositories() {
    if (github == null) {
      log.error("GitHub client is not initialized. Skipping scheduled repository crawl.");
      return;
    }

    int pageIndex = 0;
    int totalElement = 0;
    int pageSize = 10;
    boolean hasNextPage = true;

    while (hasNextPage) {
      Page<ObservedRepo> page = observedRepoService.searchRepos("", "", ObservedRepoStatus.ACTIVE,
          "", PageRequest.of(pageIndex, pageSize, Sort.by("createdAt").descending()));

      if (page.isEmpty()) {
        log.info("No more ObservedRepo entries found. Stopping crawl at page index: {}", pageIndex);
        break;
      }

      List<ObservedRepo> observedReposToUpdate = new ArrayList<>(pageSize);

      for (ObservedRepo observedRepo : page.getContent()) {
        try {
          String fullName = observedRepo.getOwner() + "/" + observedRepo.getName();

          GHRepository repo = github.getRepository(fullName);

          observedRepo.setOpenIssues(repo.getOpenIssueCount());
          observedRepo.setStars(repo.getStargazersCount());

          observedReposToUpdate.add(observedRepo);

          log.debug("[REPO CRAWLER] Fetched: {} | Open Issues: {} | Stars: {}", repo.getFullName(),
              repo.getOpenIssueCount(), repo.getStargazersCount());

        } catch (Exception e) {
          log.error("Error fetching repository details for '{}': {}", observedRepo.getName(),
              e.getMessage());
          observedRepo.setStatus(ObservedRepoStatus.INVALID);
          observedReposToUpdate.add(observedRepo);
        }
      }

      // Save updated data in a single batch
      if (!observedReposToUpdate.isEmpty()) {
        observedRepoService.saveAll(observedReposToUpdate);
        log.debug("[REPO CRAWLER] Successfully updated {} repositories in this batch.",
            observedReposToUpdate.size());
      }
      totalElement += observedReposToUpdate.size();
      hasNextPage = page.hasNext();
      pageIndex++;
    }
    log.info("[REPO CRAWLER] Successfully updated {} repositories ", totalElement);
  }
}