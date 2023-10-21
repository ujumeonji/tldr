package run.cd80.tldr.domain.user.vo;

public class AccountId {

  private final String id;

  private AccountId(String id) {
    this.id = id;
  }

  public static AccountId of(String id) {
    return new AccountId(id);
  }

  public String getId() {
    return id;
  }
}
