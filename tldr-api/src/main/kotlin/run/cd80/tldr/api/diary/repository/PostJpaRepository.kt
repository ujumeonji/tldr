package run.cd80.tldr.api.diary.repository

import org.springframework.data.repository.CrudRepository
import run.cd80.tldr.domain.post.Post

interface PostJpaRepository : CrudRepository<Post, Long>
