package run.cd80.tldr.api.diary.workflow

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import run.cd80.tldr.api.base.WorkflowScenario
import run.cd80.tldr.api.diary.application.port.inner.PostService
import run.cd80.tldr.api.diary.application.port.inner.dto.FetchPostBySlug
import run.cd80.tldr.api.diary.workflow.dto.GetDiaryBySlug
import run.cd80.tldr.api.diary.workflow.exception.PostNotFoundException

@Service
class GetDiaryBySlugWorkflow(
    private val postService: PostService,
) : WorkflowScenario<GetDiaryBySlug.Request, GetDiaryBySlug.Response>() {

    @Transactional
    override fun execute(command: GetDiaryBySlug.Request): GetDiaryBySlug.Response {
        val post = postService.fetchPostBySlug(
            FetchPostBySlug.Command(
                command.slug,
            ),
        ) ?: throw PostNotFoundException()

        return GetDiaryBySlug.Response(
            id = post.id,
            title = post.title,
            diaryAt = post.diaryAt,
            createdAt = post.createdAt,
        )
    }
}
