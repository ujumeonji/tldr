package run.cd80.tldr.api.domain.credential;

import jakarta.persistence.Entity;

@Entity
public class WakaTimeCrendential extends Credential {

  private String apiKey;

  public static WakaTimeCrendential of(String apiKey) {
    WakaTimeCrendential credential = new WakaTimeCrendential();
    credential.apiKey = apiKey;

    return credential;
  }
}
