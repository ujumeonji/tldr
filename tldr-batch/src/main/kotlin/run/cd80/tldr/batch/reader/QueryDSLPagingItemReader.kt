package run.cd80.tldr.batch.reader

import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.item.database.AbstractPagingItemReader
import org.springframework.util.ClassUtils
import org.springframework.util.CollectionUtils
import java.util.HashMap
import java.util.concurrent.CopyOnWriteArrayList

class QueryDSLPagingItemReader<T>(
    private val entityManagerFactory: EntityManagerFactory,
    private val pageSize: Int,
    private val block: (JPAQueryFactory) -> JPAQuery<T>,
) : AbstractPagingItemReader<T>() {

    private var entityManager: EntityManager? = null

    private var jpaQueryFactory: JPAQueryFactory? = null

    private var transacted: Boolean = true

    init {
        name = ClassUtils.getShortName(QueryDSLPagingItemReader::class.java)
    }

    @Throws(Exception::class)
    override fun doOpen() {
        super.doOpen()

        entityManager = entityManagerFactory.createEntityManager(HashMap<String, Any>())
        jpaQueryFactory = JPAQueryFactory(entityManager)
    }

    override fun doReadPage() {
        clearIfTransacted()
        val query = createQuery().offset((page * pageSize).toLong()).limit(pageSize.toLong())
        initResults()
        fetchQuery(query)
    }

    private fun clearIfTransacted() {
        if (transacted) {
            entityManager?.clear()
        }
    }

    private fun createQuery(): JPAQuery<T> =
        jpaQueryFactory?.let(block) ?: throw IllegalStateException("QuerydslPagingItemReader is not initialized.")

    private fun initResults() {
        if (CollectionUtils.isEmpty(results)) {
            results = CopyOnWriteArrayList()
        } else {
            results.clear()
        }
    }

    private fun fetchQuery(query: JPAQuery<T>) {
        if (!transacted) {
            val queryResult = query.fetch()
            for (entity in queryResult) {
                entityManager?.detach(entity)
                results.add(entity)
            }
        } else {
            results.addAll(query.fetch())
        }
    }

    @Throws(Exception::class)
    override fun doClose() {
        entityManager?.close()
        super.doClose()
    }
}
