package run.cd80.tldr.batch.job

import com.querydsl.core.types.Projections
import jakarta.persistence.EntityManagerFactory
import kotlinx.coroutines.runBlocking
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemStreamReader
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import run.cd80.tldr.batch.config.GithubClientConfig
import run.cd80.tldr.batch.job.dto.AlgorithmDto
import run.cd80.tldr.batch.job.dto.DiaryContent
import run.cd80.tldr.batch.listerner.ReaderFailureListener
import run.cd80.tldr.batch.listerner.WriterFailureListener
import run.cd80.tldr.batch.reader.QueryDSLPagingItemReader
import run.cd80.tldr.domain.challenge.QBojChallenge.bojChallenge
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
class AlgorithmBatchJobConfig(
    private val jobRepository: JobRepository,
    private val platformTransactionManager: PlatformTransactionManager,
    private val entityManagerFactory: EntityManagerFactory,
    private val calendar: Calendar,
    githubClientConfig: GithubClientConfig,
    httpClient: HttpClient,
) {

    private val bojCrawler = BojCrawler(httpClient)

    private val githubManager = GithubManager(
        httpClient,
        GithubOption(
            githubClientConfig.clientId,
            githubClientConfig.clientSecret,
            githubClientConfig.redirectUri,
            githubClientConfig.scopes
        ),
    )

    @Bean(JOB_NAME)
    fun algorithmBatchJob() =
        JobBuilder(JOB_NAME, jobRepository)
            .start(algorithmBatchStep())
            .build()

    @Bean(STEP_NAME)
    @JobScope
    fun algorithmBatchStep() = StepBuilder(STEP_NAME, jobRepository)
        .chunk<AlgorithmDto, AlgorithmDto>(CHUNK_SIZE, platformTransactionManager)
        .reader(algorithmBatchReader())
        .processor(algorithmBatchProcessor())
        .writer(algorithmBatchWriter())
        .listener(ReaderFailureListener { ex -> "algorithmBatchReader: ${ex.message}" })
        .listener(WriterFailureListener { ex, _ -> "algorithmBatchWriter: ${ex.message}" })
        .faultTolerant()
        .retry(Exception::class.java)
        .retryLimit(3)
        .build()

    @Bean(READER_NAME)
    @StepScope
    fun algorithmBatchReader(): ItemStreamReader<AlgorithmDto> =
        QueryDSLPagingItemReader(entityManagerFactory, CHUNK_SIZE) { queryFactory ->
            queryFactory
                .select(
                    Projections.constructor(
                        AlgorithmDto::class.java,
                        bojChallenge.username,
                        githubCredential.username.`as`("owner"),
                        githubCredential.repository,
                        githubCredential.accessToken
                    )
                )
                .from(bojChallenge)
                .innerJoin(githubCredential).on(githubCredential.account.eq(bojChallenge.account))
        }

    @Bean(PROCESSOR_NAME)
    @StepScope
    fun algorithmBatchProcessor(): ItemProcessor<in AlgorithmDto, out AlgorithmDto> =
        ItemProcessor { credential ->
            runBlocking {
                val nowDate = calendar.now().toLocalDate()
                val solutions = bojCrawler.getSolution(
                    GetSolutions.Command(credential.username),
                )

                githubManager.uploadFile(
                    UploadFile.Command(
                        "오늘의 일기 $nowDate",
                        DiaryContent(solutions),
                        "diary/${nowDate.year}/${nowDate.monthValue}/${nowDate.dayOfMonth}.md",
                    ),
                    GitRepository.of(credential.owner, credential.repository),
                    GithubAccessToken.of(credential.accessToken),
                )
            }

            credential
        }

    @Bean(WRITER_NAME)
    @StepScope
    fun algorithmBatchWriter() = ItemWriter<AlgorithmDto> { }

    companion object {

        const val JOB_NAME = "algorithmBatchJob"

        const val STEP_NAME = "algorithmBatchStep"

        const val READER_NAME = "algorithmBatchReader"

        const val PROCESSOR_NAME = "algorithmBatchProcessor"

        const val WRITER_NAME = "algorithmBatchWriter"

        const val CHUNK_SIZE = 1000
    }
}
