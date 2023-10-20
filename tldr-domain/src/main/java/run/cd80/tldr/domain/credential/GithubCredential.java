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

  protected GithubCredential() {
    super();
  }

  @Builder
  public GithubCredential(Long id, Account account, String accessToken, LocalDateTime createdAt) {
    super(id, account, createdAt);
    this.accessToken = accessToken;
  }

  public static GithubCredential of(Account account, String accessToken) {
    GithubCredential credential = new GithubCredential();
    credential.accessToken = accessToken;
    credential.setAccount(account);

    return credential;
  }
}
