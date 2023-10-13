package run.cd80.tldr.api.diary.repository

import org.springframework.stereotype.Repository
import run.cd80.tldr.api.diary.application.port.out.PostRepository
import run.cd80.tldr.api.domain.post.Post

@Repository
class PostRepositoryImpl(
    private val postJpaRepository: PostJpaRepository,
) : PostRepository {

    override fun save(post: Post): Post =
        postJpaRepository.save(post)
}
