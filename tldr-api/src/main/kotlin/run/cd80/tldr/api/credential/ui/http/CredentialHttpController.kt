package run.cd80.tldr.api.credential.ui.http

import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import run.cd80.tldr.api.auth.DefaultSignInUser
import run.cd80.tldr.api.credential.ui.http.dto.CreateGithubCredentialDto
import run.cd80.tldr.api.credential.workflow.CreateGithubCredentialWorkflow
import run.cd80.tldr.api.credential.workflow.dto.CreateGithubCredential

@RequestMapping("/credentials")
@RestController
class CredentialHttpController(
    private val createGithubCredentialWorkflow: CreateGithubCredentialWorkflow,
) {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/github/callback")
    fun createGithubCredential(
        @Valid createGithubCredentialDto: CreateGithubCredentialDto.Request,
        @AuthenticationPrincipal signInUser: DefaultSignInUser,
    ): String {
        createGithubCredentialWorkflow.execute(
            CreateGithubCredential.Request(
                signInUser.name,
                createGithubCredentialDto.code,
            ),
        )

        return "redirect:setting"
    }
}
