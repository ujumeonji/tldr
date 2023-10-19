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
import lombok.Getter;
import org.springframework.cglib.core.Local;
import run.cd80.tldr.api.domain.BaseEntity;
import run.cd80.tldr.api.domain.user.Account;

@Entity
@Getter
public class Post extends BaseEntity {
  static final String READABLE_CHARACTER_REGEX = "[^a-z0-9가-힣\\s]";

  static final String WHITESPACE_REGEX = "\\s+";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String content;

  private String slug;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id")
  private Account account;

  private LocalDateTime viewedAt;

  private LocalDateTime diaryAt;

  protected Post() {}

  @Builder
  public Post(Long id, String title, String content, Account account, LocalDateTime viewedAt,
              LocalDateTime diaryAt, LocalDateTime createdAt) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.account = account;
    this.viewedAt = viewedAt;
    this.diaryAt = diaryAt;
    setCreatedAt(createdAt);
  }

  public static Post create(String title, String content, Account account, LocalDateTime diaryAt, LocalDateTime createdAt) {
    Post post = new Post();
    post.title = title;
    post.slug = slugify(title);
    post.content = content;
    post.account = account;
    post.diaryAt = diaryAt;
    post.setCreatedAt(createdAt);

    return post;
  }

  private static String slugify(String title) {
    return title.toLowerCase().replaceAll(READABLE_CHARACTER_REGEX, "").replaceAll(WHITESPACE_REGEX, "-");
  }
}
