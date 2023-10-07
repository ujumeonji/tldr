package run.cd80.tldr.api.diary.application.port.`in`

import run.cd80.tldr.api.diary.application.port.`in`.dto.FetchPostsByMonth
import run.cd80.tldr.api.domain.post.Post

interface PostService {

    fun fetchPostsByMonth(command: FetchPostsByMonth.Command): List<Post>
}
