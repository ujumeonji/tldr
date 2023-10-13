package run.cd80.tldr.common

import jakarta.persistence.EntityManager
import run.cd80.tldr.api.domain.post.Post
import run.cd80.tldr.api.domain.user.Account
import java.time.LocalDateTime

fun EntityManager.createAccount(email: String = "test@mail.com", username: String = "test username", createdAt: LocalDateTime = LocalDateTime.now()): Account =
    Account.builder().email(email).username(username).createdAt(createdAt).build().also { this.persist(it) }

fun EntityManager.createPost(title: String = "test title", content: String = "test content", account: Account, createdAt: LocalDateTime = LocalDateTime.now()): Post =
    Post.builder().title(title).content(content).account(account).createdAt(createdAt).build().also { this.persist(it) }
