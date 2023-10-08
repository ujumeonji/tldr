package run.cd80.tldr.api.diary.application

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import run.cd80.tldr.api.diary.application.port.inner.PostService
import run.cd80.tldr.api.diary.application.port.inner.dto.FetchPostsByMonth
import run.cd80.tldr.api.diary.application.port.inner.dto.FetchPostsRecentlyViewed
import run.cd80.tldr.api.diary.application.port.out.PostQueryRepository
import run.cd80.tldr.api.domain.post.Post

@Service
@Transactional
class PostDomainService(
    private val postQueryRepository: PostQueryRepository,
) : PostService {

    override fun fetchPostsByMonth(command: FetchPostsByMonth.Command): List<Post> =
        postQueryRepository.findByMonth(command.accountId, command.now)

    override fun fetchPostsRecentlyViewed(command: FetchPostsRecentlyViewed.Command): List<Post> =
        postQueryRepository.findRecentlyViewed(command.accountId, command.count)
}
