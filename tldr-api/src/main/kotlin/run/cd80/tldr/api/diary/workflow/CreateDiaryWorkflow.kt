package run.cd80.tldr.api.diary.workflow

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import run.cd80.tldr.api.base.WorkflowScenario
import run.cd80.tldr.api.diary.application.port.inner.PostService
import run.cd80.tldr.api.diary.application.port.inner.dto.CreateDailyDiary
import run.cd80.tldr.api.diary.workflow.dto.CreateDiary
import run.cd80.tldr.api.user.application.port.inner.AccountService

@Service
class CreateDiaryWorkflow(
    private val accountService: AccountService,
    private val postService: PostService,
) : WorkflowScenario<CreateDiary.Request, CreateDiary.Response>() {

    @Transactional
    override fun execute(command: CreateDiary.Request): CreateDiary.Response {
        val account =
            accountService.findByUsername(command.username) ?: throw IllegalArgumentException("account not found")

        val post = postService.createPost(
            CreateDailyDiary.Command(
                title = command.title,
                content = command.content,
                account = account,
                date = command.date,
            ),
        )

        return CreateDiary.Response(
            id = post.id,
            title = post.title,
            content = post.content,
            createdAt = post.createdAt,
        )
    }
}
