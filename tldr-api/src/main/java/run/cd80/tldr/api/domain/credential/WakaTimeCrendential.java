package run.cd80.tldr.api.domain.credential;

import jakarta.persistence.Entity;
import run.cd80.tldr.api.domain.user.Account;

@Entity
public class WakaTimeCrendential extends Credential {

  private String apiKey;

  public static WakaTimeCrendential of(Account account, String apiKey) {
    WakaTimeCrendential credential = new WakaTimeCrendential();
    credential.apiKey = apiKey;
    credential.setAccount(account);

    return credential;
  }
}
