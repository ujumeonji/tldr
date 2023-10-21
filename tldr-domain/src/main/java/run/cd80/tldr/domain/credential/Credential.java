package run.cd80.tldr.domain.credential;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import run.cd80.tldr.domain.BaseEntity;
import run.cd80.tldr.domain.user.Account;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "provider", discriminatorType = DiscriminatorType.STRING)
public class Credential extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", nullable = false)
  private Account account;

  protected Credential() {}

  public Credential(Long id, Account account, LocalDateTime createdAt) {
    this.id = id;
    this.account = account;
    this.setCreatedAt(createdAt);
    this.setUpdatedAt(createdAt);
  }

  public Long getId() {
    return id;
  }

  protected void setAccount(Account account) {
    this.account = account;
  }
}
