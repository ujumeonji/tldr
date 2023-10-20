package run.cd80.tldr.api.credential.ui.http

import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import run.cd80.tldr.api.credential.ui.http.dto.CreateCredentialDto
import run.cd80.tldr.api.credential.workflow.RegisterProviderWorkflow
import run.cd80.tldr.api.domain.auth.DefaultSignInUser

@RequestMapping("/credentials")
@RestController
class CredentialHttpController(
    private val registerProviderWorkflow: RegisterProviderWorkflow,
) {

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    fun createGithubCredential(
        @Valid @RequestBody createGithubCredentialDto: CreateCredentialDto.Request,
        @AuthenticationPrincipal signInUser: DefaultSignInUser,
    ): CreateCredentialDto.Response {
        val credential = registerProviderWorkflow.execute(createGithubCredentialDto.toCommand(signInUser.name))

        return CreateCredentialDto.Response(
            id = credential.id,
        )
    }
}
