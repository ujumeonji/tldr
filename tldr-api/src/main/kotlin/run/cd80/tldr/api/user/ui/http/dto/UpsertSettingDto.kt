package run.cd80.tldr.api.user.ui.http.dto

object UpsertSettingDto {

    data class Request(
        val gitRepository: String,
        val bojNickname: String,
    ) {

        val owner = gitRepository.split("/")[0]

        val repository = gitRepository.split("/")[1]
    }
}
