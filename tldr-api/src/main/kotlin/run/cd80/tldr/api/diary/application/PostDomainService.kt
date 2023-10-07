package run.cd80.tldr.api.diary.application

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import run.cd80.tldr.api.diary.application.port.`in`.PostService
import run.cd80.tldr.api.diary.application.port.`in`.dto.FetchPostsByMonth
import run.cd80.tldr.api.diary.application.port.out.PostQueryRepository
import run.cd80.tldr.api.domain.post.Post

@Service
@Transactional
class PostDomainService(
    private val postQueryRepository: PostQueryRepository,
) : PostService {

    override fun fetchPostsByMonth(command: FetchPostsByMonth.Command): List<Post> =
        postQueryRepository.findByMonth(command.accountId, command.date)
}
