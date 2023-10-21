package run.cd80.tldr.batch.job.dto

import run.cd80.tldr.lib.crawler.boj.dto.GetSolutions
import run.cd80.tldr.lib.github.type.EncodeType
import run.cd80.tldr.lib.github.vo.GitContent
import java.util.*

class DiaryContent(
    private val solutions: List<GetSolutions.Result>,
) : GitContent {

    override fun contentData(): String =
        Base64.getEncoder().encodeToString(getDiaryContent().toByteArray())

    override fun encodeType(): EncodeType =
        EncodeType.BASE64

    private fun getDiaryContent(): String =
        listOf(
            "# 오늘의 일기",
            getProblemList(),
        ).joinToString("\n")

    private fun getProblemList(): String =
        listOf(
            "## 알고리즘",
            "### BOJ",
            "| 문제 번호 | 문제 제목 | 결과 | 제출 시간 |",
            "| --- | --- | --- | --- |",
            generateProblemList(),
        ).joinToString("\n")

    private fun generateProblemList(): String =
        solutions.joinToString("\n") {
            "| ${it.problemId} | ${it.problemTitle} | ${it.judgeResult} | ${it.submittedTime} |"
        }
}
