package run.cd80.tldr.batch.job.dto

import run.cd80.tldr.lib.crawler.boj.dto.GetSolutions
import run.cd80.tldr.lib.crawler.boj.type.JudgeResult
import run.cd80.tldr.lib.github.type.EncodeType
import run.cd80.tldr.lib.github.vo.GitContent
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DiaryContent(
    private val solutions: List<GetSolutions.Result>,
    private val nowDate: LocalDate = LocalDate.now(),
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
            "| 제출 | 문제 번호 | 문제 제목 | 결과 | 제출 시간 |",
            "| --- | --- | --- | --- | --- |",
            generateProblemList(),
        ).joinToString("\n")

    private fun generateProblemList(): String =
        solutions.joinToString("\n") {
            "| [${it.solutionId}](https://www.acmicpc.net/source/${it.solutionId}) | ${it.problemId} | ${it.problemTitle} | ${
                convertToBadge(
                    it.judgeResult,
                )
            } | ${convertToYYYYmmDD(it.submittedTime)} |"
        }

    private fun convertToBadge(status: JudgeResult): String = when (status) {
        JudgeResult.AC -> "![맞았습니다](https://img.shields.io/badge/맞았습니다-7AD1DD)"
        else -> "![아쉬워요](https://img.shields.io/badge/틀렸습니다-F0BBDD)"
    }

    private fun convertToYYYYmmDD(dateTime: LocalDateTime): String =
        dateTime.format(FORMAT)

    companion object {

        val FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    }
}
