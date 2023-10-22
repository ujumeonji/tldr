package run.cd80.tldr.domain.challenge;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.Builder;
import run.cd80.tldr.domain.credential.Credential;
import run.cd80.tldr.domain.user.Account;

@Entity
@DiscriminatorValue("BOJ")
public class BojChallenge extends Challenge {

  private String username;

  protected BojChallenge() {
    super();
  }

  @Builder
  public BojChallenge(Long id, Account account, String username, LocalDateTime createdAt) {
    super(id, account, createdAt);
    this.username = username;
  }

  public static BojChallenge of(Account account, String username, LocalDateTime createdAt) {
    return BojChallenge.builder().account(account).username(username).createdAt(createdAt).build();
  }
}
