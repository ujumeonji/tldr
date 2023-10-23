package run.cd80.tldr.api.user.workflow.dto

object UpsertSetting {

    data class Request(
        val username: String,
        val owner: String,
        val repository: String,
        val bojNickname: String,
    )
}
