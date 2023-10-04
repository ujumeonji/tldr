package run.cd80.tldr.api.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import run.cd80.tldr.api.domain.BaseEntity;

@Entity
@Table(name = "account", indexes = {
    @Index(name = "idx_account_identifier", columnList = "identifier")
})
public class Account extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String identifier;

  private String email;

  protected Account() {
  }

  public String getEmail() {
    return email;
  }

  public static Account signUp(String email, String identifier) {
    Account account = new Account();
    account.identifier = identifier;
    account.email = email;
    return account;
  }
}
