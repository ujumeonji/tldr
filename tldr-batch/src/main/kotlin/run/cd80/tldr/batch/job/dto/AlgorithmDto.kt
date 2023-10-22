package run.cd80.tldr.batch.job.dto

data class AlgorithmDto(
    val username: String,
    val owner: String,
    val repository: String,
    val accessToken: String,
)
