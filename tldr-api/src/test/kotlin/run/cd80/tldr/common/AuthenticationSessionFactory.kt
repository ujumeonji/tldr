package run.cd80.tldr.common

import org.springframework.data.redis.core.RedisOperations
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Component
import run.cd80.tldr.api.domain.auth.DefaultSignInUser
import run.cd80.tldr.api.domain.user.Account
import java.time.Instant
import java.util.*

@Component
class AuthenticationSessionFactory(
    private val sessionRedisOperations: RedisOperations<String, Any>,
) {

    fun create(account: Account): String {
        val uuid = UUID.randomUUID().toString()
        val key: String = SESSION_PREFIX + uuid

        val delta = HashMap<String, Any>()
        delta[CREATION_TIME_KEY] = Instant.now().toEpochMilli()
        delta[MAX_INACTIVE_INTERVAL_KEY] = 1800
        delta[LAST_ACCESSED_TIME_KEY] = Instant.now().toEpochMilli()

        val authentication = OAuth2AuthenticationToken(
            DefaultSignInUser(account.email),
            emptyList(),
            "google",
        )
        val securityContext = SecurityContextImpl(authentication)
        delta["$ATTRIBUTE_PREFIX$SECURITY_CONTEXT_KEY"] = securityContext

        sessionRedisOperations.opsForHash<String, Any>().putAll(key, delta)

        return Base64.getEncoder().encodeToString(uuid.toByteArray())
    }

    companion object {

        const val SESSION_PREFIX = "spring:session:sessions:"

        const val SECURITY_CONTEXT_KEY = "SPRING_SECURITY_CONTEXT"

        const val CREATION_TIME_KEY = "creationTime"

        const val LAST_ACCESSED_TIME_KEY = "lastAccessedTime"

        const val MAX_INACTIVE_INTERVAL_KEY = "maxInactiveInterval"

        const val ATTRIBUTE_PREFIX = "sessionAttr:"
    }
}
