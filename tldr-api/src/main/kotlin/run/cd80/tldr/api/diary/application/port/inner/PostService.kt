package run.cd80.tldr.api.diary.application.port.inner

import run.cd80.tldr.api.diary.application.port.inner.dto.FetchPostsByMonth
import run.cd80.tldr.api.diary.application.port.inner.dto.FetchPostsRecentViewed
import run.cd80.tldr.api.domain.post.Post

interface PostService {

    fun fetchPostsByMonth(command: FetchPostsByMonth.Command): List<Post>

    fun fetchPostsRecentViewed(command: FetchPostsRecentViewed.Command): List<Post>
}
