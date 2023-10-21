package run.cd80.tldr.batch.job

import jakarta.persistence.EntityManagerFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import run.cd80.tldr.batch.config.GithubClientConfig
import run.cd80.tldr.batch.job.dto.DiaryContent
import run.cd80.tldr.batch.listerner.ReaderFailureListener
import run.cd80.tldr.batch.listerner.WriterFailureListener
import run.cd80.tldr.batch.reader.QueryDSLPagingItemReader
import run.cd80.tldr.domain.credential.GithubCredential
import run.cd80.tldr.domain.credential.QGithubCredential.githubCredential
import run.cd80.tldr.lib.calendar.Calendar
import run.cd80.tldr.lib.crawler.boj.BojCrawler
import run.cd80.tldr.lib.crawler.boj.dto.GetSolutions
import run.cd80.tldr.lib.github.GithubManager
import run.cd80.tldr.lib.github.GithubOption
import run.cd80.tldr.lib.github.dto.UploadFile
import run.cd80.tldr.lib.github.vo.GitRepository
import run.cd80.tldr.lib.github.vo.GithubAccessToken
import run.cd80.tldr.lib.http.HttpClient

@Configuration
class AlgorithmBatchJob(
    private val jobRepository: JobRepository,
    private val platformTransactionManager: PlatformTransactionManager,
    private val entityManagerFactory: EntityManagerFactory,
    private val githubClientConfig: GithubClientConfig,
    private val httpClient: HttpClient,
    private val calendar: Calendar,
) {

    private val bojCrawler = BojCrawler(httpClient)

    private val githubManager = GithubManager(
        httpClient,
        GithubOption(githubClientConfig.clientId, githubClientConfig.clientSecret, githubClientConfig.redirectUri, githubClientConfig.scopes),
    )

    @Bean
    fun algorithmBatchJob() =
        JobBuilder(JOB_NAME, jobRepository)
            .start(algorithmBatchStep())
            .build()

    @Bean
    @StepScope
    fun algorithmBatchStep() = StepBuilder(STEP_NAME, jobRepository)
        .chunk<GithubCredential, GithubCredential>(CHUNK_SIZE, platformTransactionManager)
        .reader(algorithmBatchReader())
        .processor(algorithmBatchProcessor())
        .listener(ReaderFailureListener { ex -> "algorithmBatchReader: ${ex.message}" })
        .listener(WriterFailureListener { ex, items -> "algorithmBatchWriter: ${ex.message} id: ${items.first().id} chunkSize: $CHUNK_SIZE" })
        .faultTolerant()
        .retry(Exception::class.java)
        .retryLimit(3)
        .build()

    @Bean
    @StepScope
    fun algorithmBatchReader(): ItemReader<GithubCredential> =
        QueryDSLPagingItemReader(entityManagerFactory, CHUNK_SIZE) { queryFactory ->
            queryFactory
                .selectFrom(githubCredential)
                .where(githubCredential.deletedAt.isNull)
        }

    @Bean
    @StepScope
    fun algorithmBatchProcessor(): ItemProcessor<in GithubCredential, out GithubCredential> =
        ItemProcessor { credential ->
            runBlocking {
                val nowDate = calendar.now().toLocalDate()
                val solutions = bojCrawler.getSolution(
                    GetSolutions.Command("test-boj-username"),
                )

                githubManager.uploadFile(
                    UploadFile.Command(
                        "오늘의 일기 $nowDate",
                        DiaryContent(solutions),
                        "diary/${nowDate.year}/${nowDate.monthValue}/${nowDate.dayOfMonth}.md",
                    ),
                    GitRepository.of("test-owner", "test-repo"),
                    GithubAccessToken.of("test-access-token"),
                )
            }

            credential
        }

    @Bean
    @StepScope
    fun algorithmBatchWriter() {
    }

    companion object {

        const val JOB_NAME = "algorithmBatchJob"

        const val STEP_NAME = "algorithmBatchStep"

        const val CHUNK_SIZE = 1000
    }
}
