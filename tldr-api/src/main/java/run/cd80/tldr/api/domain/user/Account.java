package run.cd80.tldr.api.domain.user;

import jakarta.persistence.*;
import org.springframework.util.Assert;
import run.cd80.tldr.api.domain.BaseEntity;

import java.util.UUID;

@Entity
@Table(name = "account", uniqueConstraints = {
  @UniqueConstraint(name = "uni_account_username", columnNames = "username")
})
public class Account extends BaseEntity {

  private static final String MAIL_SPLITTER = "@";

  private static final String USERNAME_SPLITTER = "#";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;

  private String username;

  protected Account() {
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getUsername() {
    return username;
  }

  public static Account signUp(final String email) {
    Assert.hasText(email, "email must not be empty");

    Account account = new Account();
    account.username = generateUsername(email);
    account.email = email;
    return account;
  }

  private static String generateUsername(String email) {
    return email.split(MAIL_SPLITTER)[0] + USERNAME_SPLITTER + UUID.randomUUID().toString().substring(0, 8);
  }
}
