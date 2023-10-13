package run.cd80.tldr.api.diary.application

import org.springframework.stereotype.Service
import run.cd80.tldr.api.diary.application.port.inner.PostService
import run.cd80.tldr.api.diary.application.port.inner.dto.CreateDailyDiary
import run.cd80.tldr.api.diary.application.port.inner.dto.FetchPostsByMonth
import run.cd80.tldr.api.diary.application.port.inner.dto.FetchPostsRecentlyViewed
import run.cd80.tldr.api.diary.application.port.out.PostQueryRepository
import run.cd80.tldr.api.diary.application.port.out.PostRepository
import run.cd80.tldr.api.domain.post.Post

@Service
class PostDomainService(
    private val postQueryRepository: PostQueryRepository,
    private val postRepository: PostRepository,
) : PostService {

    override fun fetchPostsByMonth(command: FetchPostsByMonth.Command): List<Post> =
        postQueryRepository.findByMonth(command.accountId, command.now)

    override fun fetchPostsRecentlyViewed(command: FetchPostsRecentlyViewed.Command): List<Post> =
        postQueryRepository.findRecentlyViewed(command.accountId, command.count)

    override fun createPost(command: CreateDailyDiary.Command): Post =
        Post
            .create(command.title, command.content, command.account)
            .also(postRepository::save)
}
