package run.cd80.tldr.api.domain.user.vo;

public class AccountId {

  private Long id;

  private AccountId(Long id) {
    this.id = id;
  }

  static AccountId of(Long id) {
    return new AccountId(id);
  }

  public Long getId() {
    return id;
  }
}
