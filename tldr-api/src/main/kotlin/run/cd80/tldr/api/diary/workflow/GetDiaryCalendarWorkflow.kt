package run.cd80.tldr.api.diary.workflow

import org.springframework.stereotype.Component
import run.cd80.tldr.api.base.WorkflowScenario
import run.cd80.tldr.api.diary.application.port.inner.PostService
import run.cd80.tldr.api.diary.application.port.inner.dto.FetchPostsByMonth
import run.cd80.tldr.api.diary.workflow.dto.GetDiaryCalendar
import run.cd80.tldr.api.domain.post.Post
import run.cd80.tldr.api.domain.user.vo.AccountId

@Component
class GetDiaryCalendarWorkflow(
    private val postService: PostService,
) : WorkflowScenario<GetDiaryCalendar.Request, GetDiaryCalendar.Response>() {

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
            createdDate = post.createdAt,
        )
    }
}
