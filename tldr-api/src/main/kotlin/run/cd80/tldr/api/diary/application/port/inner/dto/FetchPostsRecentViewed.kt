package run.cd80.tldr.api.diary.application.port.inner.dto

import run.cd80.tldr.api.domain.user.vo.AccountId

class FetchPostsRecentViewed {

    data class Command(
        val accountId: AccountId,
        val count: Int,
    )
}
