package run.cd80.tldr.lib.crawler.boj.dto

import run.cd80.tldr.lib.crawler.boj.type.JudgeResult
import java.time.LocalDateTime

object GetSolutions {

    data class Command(val username: String)

    data class Result(
        val solutionId: Long,
        val problemId: Long,
        val submittedTime: LocalDateTime,
        val problemTitle: String,
        val judgeResult: JudgeResult,
    )
}
