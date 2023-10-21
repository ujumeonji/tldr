package run.cd80.tldr.api.diary.workflow

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import run.cd80.tldr.api.base.WorkflowScenario
import run.cd80.tldr.api.diary.application.port.inner.PostService
import run.cd80.tldr.api.diary.application.port.inner.dto.FetchPostsByMonth
import run.cd80.tldr.api.diary.workflow.dto.GetDiaryCalendar
import run.cd80.tldr.domain.post.Post
import run.cd80.tldr.domain.user.vo.AccountId

@Service
class GetDiaryCalendarWorkflow(
    private val postService: PostService,
) : WorkflowScenario<GetDiaryCalendar.Request, GetDiaryCalendar.Response>() {

    @Transactional
    override fun execute(command: GetDiaryCalendar.Request): GetDiaryCalendar.Response {
        val posts = postService.fetchPostsByMonth(
            FetchPostsByMonth.Command(
                command.now,
                AccountId.of(command.username),
            ),
        )

        return GetDiaryCalendar.Response(
            posts.map(::toItem),
        )
    }

    private fun toItem(post: Post): GetDiaryCalendar.Response.Item {
        return GetDiaryCalendar.Response.Item(
            id = post.id,
            title = post.title,
            createdAt = post.createdAt,
        )
    }
}
