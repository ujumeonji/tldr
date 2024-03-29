package run.cd80.tldr.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint
import run.cd80.tldr.api.config.oauth2.OAuth2UserSignUpService

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val oauth2UserSignUpService: OAuth2UserSignUpService,
) {

    @Bean
    fun filter(
        httpSecurity: HttpSecurity,
    ): SecurityFilterChain =
        httpSecurity
            .apply(::disableSecurityConfig)
            .apply(::initializeOAuth2Login)
            .apply(::initializeHttpBasic)
            .build()

    private fun disableSecurityConfig(httpSecurity: HttpSecurity): HttpSecurity =
        httpSecurity
            .csrf {
                it.disable()
            }
            .formLogin {
                it.disable()
            }
            .logout {
                it.disable()
            }
            .httpBasic {
                it.disable()
            }

    private fun initializeHttpBasic(httpSecurity: HttpSecurity): HttpSecurity =
        httpSecurity
            .sessionManagement { sessionManagementConfigurer ->
                sessionManagementConfigurer
                    .sessionFixation { sessionFixationConfigurer ->
                        sessionFixationConfigurer
                            .changeSessionId()
                    }
            }
            .exceptionHandling {
                it.authenticationEntryPoint(Http403ForbiddenEntryPoint())
            }

    private fun initializeOAuth2Login(httpSecurity: HttpSecurity): HttpSecurity =
        httpSecurity
            .oauth2Login { oAuth2LoginConfigurer ->
                oAuth2LoginConfigurer
                    .authorizationEndpoint {
                        it.baseUri("/oauth2")
                    }
                    .userInfoEndpoint {
                        it.userService(oauth2UserSignUpService)
                    }
            }
}
