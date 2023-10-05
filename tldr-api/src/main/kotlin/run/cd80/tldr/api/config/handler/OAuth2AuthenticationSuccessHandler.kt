package run.cd80.tldr.api.config.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import run.cd80.tldr.api.base.WorkflowScenario
import run.cd80.tldr.api.user.workflow.dto.FindOrCreateAccount

@Component
class OAuth2AuthenticationSuccessHandler(
    private val findOrCreateAccountWorkflow: WorkflowScenario<FindOrCreateAccount.Request, FindOrCreateAccount.Response>,
) : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?,
    ) {
        val principal = authentication?.principal as DefaultOAuth2User?
        findOrCreateAccountWorkflow.execute(createRequest(principal))

        super.onAuthenticationSuccess(request, response, authentication)
    }

    private fun createRequest(principal: DefaultOAuth2User?): FindOrCreateAccount.Request {
        val emailVerified = principal?.attributes?.get("email_verified") as Boolean? ?: false
        if (!emailVerified) {
            throw RuntimeException("Email not verified")
        }

        return FindOrCreateAccount.Request(
            email = principal?.attributes?.get("email") as String? ?: "",
            identifier = principal?.name ?: "",
        )
    }
}
