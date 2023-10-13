package run.cd80.tldr.api.diary.application.port.out

import run.cd80.tldr.api.domain.post.Post

interface PostRepository {

    fun save(post: Post): Post
}
