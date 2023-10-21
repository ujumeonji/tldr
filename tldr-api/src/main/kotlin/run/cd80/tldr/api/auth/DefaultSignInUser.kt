package run.cd80.tldr.api.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class DefaultSignInUser(private val username: String) : SignInUser {

    override fun getName(): String =
        username

    override fun getAttributes(): MutableMap<String, Any> =
        mutableMapOf()

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
}
