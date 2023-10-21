package run.cd80.tldr.api.config.oauth2

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import run.cd80.tldr.api.auth.DefaultSignInUser
import run.cd80.tldr.api.base.WorkflowScenario
import run.cd80.tldr.api.user.workflow.dto.FindOrCreateAccount

@Service
class OAuth2UserSignUpService(
    private val findOrCreateAccountWorkflow: WorkflowScenario<FindOrCreateAccount.Request, FindOrCreateAccount.Response>,
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private val delegateUserService = DefaultOAuth2UserService()

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val user = delegateUserService.loadUser(userRequest)
        val result = findOrCreateAccountWorkflow.execute(createRequest(user))

        return DefaultSignInUser(result.username)
    }

    private fun createRequest(principal: OAuth2User?): FindOrCreateAccount.Request {
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
