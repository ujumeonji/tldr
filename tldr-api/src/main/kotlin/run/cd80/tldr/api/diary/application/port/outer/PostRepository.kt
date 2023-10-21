package run.cd80.tldr.api.diary.application.port.outer

import run.cd80.tldr.domain.post.Post

interface PostRepository {

    fun save(post: Post): Post
}
