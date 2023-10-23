package run.cd80.tldr.domain.credential;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.Builder;
import run.cd80.tldr.domain.user.Account;

@Entity
@DiscriminatorValue("GITHUB")
public class GithubCredential extends Credential {

  private String accessToken;

  private String username;

  private String repository;

  protected GithubCredential() {
    super();
  }

  @Builder
  public GithubCredential(
      Long id,
      Account account,
      String accessToken,
      String username,
      String repository,
      LocalDateTime createdAt) {
    super(id, account, createdAt);
    this.accessToken = accessToken;
    this.username = username;
    this.repository = repository;
  }

  public static GithubCredential of(Account account, String accessToken) {
    GithubCredential credential = new GithubCredential();
    credential.setAccount(account);
    credential.accessToken = accessToken;

    return credential;
  }

  public void update(String username, String repository) {
    this.username = username;
    this.repository = repository;
  }
}
