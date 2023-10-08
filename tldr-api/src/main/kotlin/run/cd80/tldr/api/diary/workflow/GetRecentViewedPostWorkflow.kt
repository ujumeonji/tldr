package run.cd80.tldr.api.diary.workflow

import org.springframework.stereotype.Component
import run.cd80.tldr.api.base.WorkflowScenario
import run.cd80.tldr.api.diary.application.port.inner.PostService
import run.cd80.tldr.api.diary.application.port.inner.dto.FetchPostsRecentViewed
import run.cd80.tldr.api.diary.workflow.dto.GetRecentViewed
import run.cd80.tldr.api.domain.post.Post
import run.cd80.tldr.api.domain.user.vo.AccountId

@Component
class GetRecentViewedPostWorkflow(
    private val postService: PostService,
) : WorkflowScenario<GetRecentViewed.Request, GetRecentViewed.Response>() {

    override fun execute(command: GetRecentViewed.Request): GetRecentViewed.Response {
        val posts = postService.fetchPostsRecentViewed(
            FetchPostsRecentViewed.Command(
                AccountId.of(command.accountId),
                command.count,
            ),
        )

        return GetRecentViewed.Response(
            posts.map(::toItem),
        )
    }

    private fun toItem(post: Post): GetRecentViewed.Response.Item {
        return GetRecentViewed.Response.Item(
            id = post.id,
            title = post.title,
            content = post.content,
            createdDate = post.createdAt,
        )
    }
}
