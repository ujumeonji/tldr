package run.cd80.tldr.api.domain.credential;

import jakarta.persistence.Entity;

@Entity
public class GithubCredential extends Credential {

  private String accessToken;

  public static GithubCredential of(String accessToken) {
    GithubCredential credential = new GithubCredential();
    credential.accessToken = accessToken;

    return credential;
  }
}
