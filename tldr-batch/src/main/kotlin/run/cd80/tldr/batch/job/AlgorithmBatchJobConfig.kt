package run.cd80.tldr.batch.job

import kotlinx.coroutines.runBlocking
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JdbcPagingItemReader
import org.springframework.batch.item.database.Order
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.transaction.PlatformTransactionManager
import run.cd80.tldr.batch.config.GithubClientConfig
import run.cd80.tldr.batch.extend.toDate
import run.cd80.tldr.batch.job.dto.AlgorithmDto
import run.cd80.tldr.batch.job.dto.DiaryContent
import run.cd80.tldr.batch.listerner.ReaderFailureListener
import run.cd80.tldr.batch.listerner.WriterFailureListener
import run.cd80.tldr.lib.calendar.Calendar
import run.cd80.tldr.lib.crawler.boj.BojCrawler
import run.cd80.tldr.lib.crawler.boj.dto.GetSolutions
import run.cd80.tldr.lib.github.GithubManager
import run.cd80.tldr.lib.github.GithubOption
import run.cd80.tldr.lib.github.dto.UploadFile
import run.cd80.tldr.lib.github.vo.GitRepository
import run.cd80.tldr.lib.github.vo.GithubAccessToken
import run.cd80.tldr.lib.http.HttpClient
import java.time.LocalDate
import javax.sql.DataSource

@Configuration
class AlgorithmBatchJobConfig(
    private val jobRepository: JobRepository,
    private val platformTransactionManager: PlatformTransactionManager,
    private val calendar: Calendar,
    private val dataSource: DataSource,
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
            githubClientConfig.scopes,
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
        .processor(algorithmBatchProcessor(null))
        .writer(algorithmBatchWriter())
        .listener(ReaderFailureListener { ex -> "algorithmBatchReader: ${ex.message}" })
        .listener(WriterFailureListener { ex, _ -> "algorithmBatchWriter: ${ex.message}" })
        .faultTolerant()
        .retry(Exception::class.java)
        .retryLimit(3)
        .build()

    @Bean(READER_NAME)
    @StepScope
    fun algorithmBatchReader(): JdbcPagingItemReader<AlgorithmDto> =
        JdbcPagingItemReaderBuilder<AlgorithmDto>()
            .name("courseDurationSyncBatchReader")
            .pageSize(CHUNK_SIZE)
            .fetchSize(CHUNK_SIZE)
            .dataSource(dataSource)
            .queryProvider(createQueryProvider())
            .rowMapper(DataClassRowMapper(AlgorithmDto::class.java))
            .build()

    @Bean(PROCESSOR_NAME)
    @StepScope
    fun algorithmBatchProcessor(@Value("#{jobParameters[requestDate]}") requestDate: String?): ItemProcessor<in AlgorithmDto, out AlgorithmDto> =
        ItemProcessor { credential ->
            runBlocking {
                val nowDate = requestDate?.toDate() ?: LocalDate.now()
                val solutions = bojCrawler.getSolution(
                    GetSolutions.Command(credential.username),
                ).filter {
                    it.submittedTime.toLocalDate() == nowDate
                }

                if (solutions.isNotEmpty()) {
                    githubManager.uploadFile(
                        UploadFile.Command(
                            "오늘의 일기 $nowDate",
                            DiaryContent(solutions, nowDate),
                            "일기장/${nowDate.year}/${nowDate.monthValue}/${nowDate.dayOfMonth}.md",
                        ),
                        GitRepository.of(credential.owner, credential.repository),
                        GithubAccessToken.of(credential.accessToken),
                    )
                }
            }

            credential
        }

    @Bean(WRITER_NAME)
    @StepScope
    fun algorithmBatchWriter() = ItemWriter<AlgorithmDto> { }

    private fun createQueryProvider() = SqlPagingQueryProviderFactoryBean().apply {
        setDataSource(dataSource)
        setSelectClause(
            """
                b.id as id,
                b.username as username, 
                g.username as owner, 
                g.repository as repository, 
                g.access_token as access_token
            """.trimIndent(),
        )
        setFromClause(
            """
                from boj_challenge b
                join github_credential g on g.account_id = b.account_id
            """.trimIndent(),
        )
        setWhereClause("b.deleted_at is null")
        setSortKeys(mapOf("id" to Order.DESCENDING))
    }.`object`

    companion object {

        const val JOB_NAME = "algorithmBatchJob"

        const val STEP_NAME = "algorithmBatchStep"

        const val READER_NAME = "algorithmBatchReader"

        const val PROCESSOR_NAME = "algorithmBatchProcessor"

        const val WRITER_NAME = "algorithmBatchWriter"

        const val CHUNK_SIZE = 1000
    }
}
