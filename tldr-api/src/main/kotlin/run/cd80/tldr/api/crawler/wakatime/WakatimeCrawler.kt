package run.cd80.tldr.api.crawler.wakatime

import com.google.gson.Gson
import org.springframework.stereotype.Component
import run.cd80.tldr.api.crawler.wakatime.dto.GetTaskRecord
import run.cd80.tldr.api.crawler.wakatime.response.DurationResponse
import run.cd80.tldr.core.http.HttpClient
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Component
class WakatimeCrawler constructor(
    private val httpClient: HttpClient,
) {

    suspend fun getTaskRecords(command: GetTaskRecord.Command): List<GetTaskRecord.Result> {
        val base64ApiKey = encodeBase64(command.apiKey)

        return getDurationsFromWakatime(base64ApiKey, command.date)
    }

    private suspend fun getDurationsFromWakatime(base64ApiKey: String, date: LocalDate): List<GetTaskRecord.Result> =
        try {
            val response = httpClient
                .get(DURATION_API_ENDPOINT)
                .queryParam("date", "$date")
                .header("Authorization", "Basic $base64ApiKey")
                .execute()

            Gson().fromJson(response.body, DurationResponse::class.java).data.map { duration ->
                GetTaskRecord.Result(
                    project = duration.project,
                    duration = duration.duration,
                    workedDateTime = LocalDateTime.ofEpochSecond(duration.time.toLong(), 0, ZoneOffset.UTC)
                )
            }
        } catch (e: Exception) {
            emptyList()
        }

    @OptIn(ExperimentalEncodingApi::class)
    private fun encodeBase64(str: String): String =
        Base64.encode(str.toByteArray())

    companion object {

        private const val DURATION_API_ENDPOINT = "https://wakatime.com/api/v1/users/current/durations"
    }
}
