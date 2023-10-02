package run.cd80.tldr.api.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@ConfigurationProperties(prefix = "github")
@Validated
class GithubConfig {

    lateinit var clientId: String

    lateinit var clientSecret: String

    lateinit var redirectUri: String

    lateinit var scopes: List<String>
}
