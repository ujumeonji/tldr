package run.cd80.tldr.domain.credential;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.Builder;
import run.cd80.tldr.domain.user.Account;

@Entity
@DiscriminatorValue("WAKATIME")
public class WakaTimeCrendential extends Credential {

  private String apiKey;

  protected WakaTimeCrendential() {
    super();
  }

  @Builder
  public WakaTimeCrendential(Long id, Account account, String apiKey, LocalDateTime createdAt) {
    super(id, account, createdAt);
    this.apiKey = apiKey;
  }

  public static WakaTimeCrendential of(Account account, String apiKey) {
    WakaTimeCrendential credential = new WakaTimeCrendential();
    credential.apiKey = apiKey;
    credential.setAccount(account);

    return credential;
  }
}
