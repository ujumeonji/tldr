package run.cd80.tldr.batch.listerner

import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.batch.core.ItemProcessListener
import org.springframework.batch.core.ItemReadListener
import org.springframework.batch.core.ItemWriteListener
import org.springframework.batch.item.Chunk

class ReaderFailureListener<T>(
    private val block: (Exception) -> String,
) : ItemReadListener<T> {

    private val logger: Logger = getLogger(ReaderFailureListener::class.java)

    override fun onReadError(ex: Exception) {
        logger.error(block(ex))
    }
}

class ProcessorFailureListener<T, S>(
    private val block: (T, Exception) -> String,
) : ItemProcessListener<T, S> {

    private val logger: Logger = getLogger(ReaderFailureListener::class.java)

    override fun onProcessError(item: T & Any, e: Exception) {
        logger.error(block(item, e))
    }
}

class WriterFailureListener<T>(
    private val block: (Exception, Chunk<out T>) -> String,
) : ItemWriteListener<T> {

    private val logger: Logger = getLogger(ReaderFailureListener::class.java)

    override fun onWriteError(exception: Exception, items: Chunk<out T>) {
        logger.error(block(exception, items))
    }
}
