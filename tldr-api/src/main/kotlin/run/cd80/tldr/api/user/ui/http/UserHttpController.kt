package run.cd80.tldr.api.user.ui.http

import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import run.cd80.tldr.api.auth.DefaultSignInUser
import run.cd80.tldr.api.user.ui.http.dto.UpsertSettingDto
import run.cd80.tldr.api.user.workflow.UpsertSettingWorkflow
import run.cd80.tldr.api.user.workflow.dto.UpsertSetting

@RequestMapping("/users")
@RestController
class UserHttpController(
    private val upsertSettingWorkflow: UpsertSettingWorkflow,
) {

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/setting")
    fun upsertSetting(
        @Valid @RequestParam upsertSettingDto: UpsertSettingDto.Request,
        @AuthenticationPrincipal signInUser: DefaultSignInUser,
    ) {
        upsertSettingWorkflow.execute(
            UpsertSetting.Request(
                signInUser.name,
                upsertSettingDto.owner,
                upsertSettingDto.repository,
                upsertSettingDto.bojNickname,
            ),
        )
    }
}
