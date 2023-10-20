package run.cd80.tldr.common

import jakarta.persistence.EntityManager
import run.cd80.tldr.api.domain.credential.Credential
import run.cd80.tldr.api.domain.credential.GithubCredential
import run.cd80.tldr.api.domain.credential.Provider
import run.cd80.tldr.api.domain.credential.WakaTimeCrendential
import run.cd80.tldr.api.domain.post.Post
import run.cd80.tldr.api.domain.user.Account
import java.time.LocalDateTime

fun EntityManager.createAccount(email: String = "test@mail.com", username: String = "test username", createdAt: LocalDateTime = LocalDateTime.now()): Account =
    Account.builder().email(email).username(username).createdAt(createdAt).build().also { this.persist(it) }

fun EntityManager.createPost(title: String = "test title", content: String = "test content", account: Account, createdAt: LocalDateTime = LocalDateTime.now()): Post =
    Post.builder().title(title).content(content).account(account).createdAt(createdAt).build().also { this.persist(it) }

fun EntityManager.createGithubCredential(account: Account, accessToken: String = "test-access-token", createdAt: LocalDateTime = LocalDateTime.now()): Credential =
    GithubCredential.builder().account(account).accessToken(accessToken).createdAt(createdAt).build().also { this.persist(it) }

fun EntityManager.createWakaTimeCredential(account: Account, apiKey: String = "test-api-key", createdAt: LocalDateTime = LocalDateTime.now()): Credential =
    WakaTimeCrendential.builder().account(account).apiKey(apiKey).createdAt(createdAt).build().also { this.persist(it) }
