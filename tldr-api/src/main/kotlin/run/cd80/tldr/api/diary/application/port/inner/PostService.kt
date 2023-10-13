package run.cd80.tldr.api.diary.application.port.inner

import run.cd80.tldr.api.diary.application.port.inner.dto.CreateDailyDiary
import run.cd80.tldr.api.diary.application.port.inner.dto.FetchPostsByMonth
import run.cd80.tldr.api.diary.application.port.inner.dto.FetchPostsRecentlyViewed
import run.cd80.tldr.api.domain.post.Post

interface PostService {

    fun fetchPostsByMonth(command: FetchPostsByMonth.Command): List<Post>

    fun fetchPostsRecentlyViewed(command: FetchPostsRecentlyViewed.Command): List<Post>

    fun createPost(command: CreateDailyDiary.Command): Post
}
