package run.cd80.tldr.api.crawler.boj.dto

object GetSolutions {

    data class Command(val username: String)

    data class Result(val problemId: Long, val submittedTime: Long)
}
