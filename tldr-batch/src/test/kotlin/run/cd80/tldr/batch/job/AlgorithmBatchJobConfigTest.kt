package run.cd80.tldr.batch.job

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import run.cd80.tldr.batch.config.AlgorithmBatchJobTestConfig
import run.cd80.tldr.batch.config.DatabaseTestConfig

@SpringBatchTest
@SpringBootTest(classes = [AlgorithmBatchJobConfig::class, AlgorithmBatchJobTestConfig::class, DatabaseTestConfig::class])
@ActiveProfiles("test")
class AlgorithmBatchJobConfigTest @Autowired constructor(
    private val jobLauncherTestUtils: JobLauncherTestUtils,
) : StringSpec() {

    init {

        "알고리즘 배치 잡을 실행한다" {
            // given
            val jobParameters =
                JobParametersBuilder()
                    .addString("date", "20221024")
                    .toJobParameters()

            // when
            val result = jobLauncherTestUtils.launchJob(jobParameters)

            // then
            result.exitStatus.exitCode shouldBe "COMPLETED"
        }
    }
}
