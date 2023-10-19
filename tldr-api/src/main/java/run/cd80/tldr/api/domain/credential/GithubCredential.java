package run.cd80.tldr.api.domain.credential;

import jakarta.persistence.Entity;
import run.cd80.tldr.api.domain.user.Account;

@Entity
public class GithubCredential extends Credential {

  private String accessToken;

  public static GithubCredential of(Account account, String accessToken) {
    GithubCredential credential = new GithubCredential();
    credential.accessToken = accessToken;
    credential.setAccount(account);

    return credential;
  }
}
