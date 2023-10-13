package run.cd80.tldr.api.domain.post;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

import lombok.Builder;
import run.cd80.tldr.api.domain.BaseEntity;
import run.cd80.tldr.api.domain.user.Account;

@Entity
public class Post extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id")
  private Account account;

  private LocalDateTime viewedAt;

  protected Post() {}

  @Builder
  public Post(Long id, String title, String content, Account account, LocalDateTime viewedAt, LocalDateTime createdAt) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.account = account;
    this.viewedAt = viewedAt;
    setCreatedAt(createdAt);
  }

  public static Post create(String title, String content, Account account) {
    Post post = new Post();
    post.title = title;
    post.content = content;
    post.account = account;
    return post;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }
}
