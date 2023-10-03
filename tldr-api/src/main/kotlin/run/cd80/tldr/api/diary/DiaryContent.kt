package run.cd80.tldr.api.diary

import run.cd80.tldr.api.crawler.boj.dto.GetSolutions
import run.cd80.tldr.api.crawler.wakatime.dto.GetTaskRecord
import run.cd80.tldr.api.manager.github.type.EncodeType
import run.cd80.tldr.api.manager.github.vo.GitContent
import java.util.*

class DiaryContent(
    private val summaries: List<GetTaskRecord.Result>,
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
            getSummaryList(),
        ).joinToString("\n")

    private fun getProblemList(): String =
        listOf(
            "## 오늘의 알고리즘",
            "### BOJ",
            "| 문제 번호 | 문제 제목 | 결과 | 제출 시간 |",
            "| --- | --- | --- | --- |",
            generateProblemList(),
        ).joinToString("\n")

    private fun generateProblemList(): String =
        solutions.joinToString("\n") {
            "| ${it.problemId} | ${it.problemTitle} | ${it.judgeResult} | ${it.submittedTime} |"
        }

    private fun getSummaryList(): String =
        listOf(
            "## 오늘의 코딩",
            "### WakaTime",
            "| 프로젝트 | 시간 |",
            "| --- | --- |",
            generateSummaryList(),
        ).joinToString("\n")

    private fun generateSummaryList(): String =
        summaries.joinToString("\n") {
            "| ${it.project} | ${it.duration} |"
        }
}
