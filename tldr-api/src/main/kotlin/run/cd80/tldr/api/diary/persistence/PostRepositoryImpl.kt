package run.cd80.tldr.api.diary.persistence

import org.springframework.stereotype.Repository
import run.cd80.tldr.api.diary.application.port.outer.PostRepository
import run.cd80.tldr.domain.post.Post

@Repository
class PostRepositoryImpl(
    private val postJpaRepository: PostJpaRepository,
) : PostRepository {

    override fun save(post: Post): Post =
        postJpaRepository.save(post)
}
