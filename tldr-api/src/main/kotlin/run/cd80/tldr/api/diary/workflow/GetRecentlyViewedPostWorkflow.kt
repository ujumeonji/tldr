package run.cd80.tldr.api.diary.workflow

import org.springframework.stereotype.Component
import run.cd80.tldr.api.base.WorkflowScenario
import run.cd80.tldr.api.diary.application.port.inner.PostService
import run.cd80.tldr.api.diary.application.port.inner.dto.FetchPostsRecentlyViewed
import run.cd80.tldr.api.diary.workflow.dto.GetRecentlyViewed
import run.cd80.tldr.domain.post.Post
import run.cd80.tldr.domain.user.vo.AccountId

@Component
class GetRecentlyViewedPostWorkflow(
    private val postService: PostService,
) : WorkflowScenario<GetRecentlyViewed.Request, GetRecentlyViewed.Response>() {

    override fun execute(command: GetRecentlyViewed.Request): GetRecentlyViewed.Response {
        val posts = postService.fetchPostsRecentlyViewed(
            FetchPostsRecentlyViewed.Command(
                AccountId.of(command.username),
                command.count,
            ),
        )

        return GetRecentlyViewed.Response(
            posts.map(::toItem),
        )
    }

    private fun toItem(post: Post): GetRecentlyViewed.Response.Item {
        return GetRecentlyViewed.Response.Item(
            id = post.id,
            title = post.title,
            content = post.content,
            createdAt = post.createdAt,
        )
    }
}
