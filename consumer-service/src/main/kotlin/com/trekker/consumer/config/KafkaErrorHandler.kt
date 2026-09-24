package com.trekker.consumer.config

import com.trekker.consumer.domain.FailedEventEntity
import com.trekker.consumer.domain.FailedEventRepository
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.listener.CommonErrorHandler
import org.springframework.kafka.listener.MessageListenerContainer
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.UUID

@Component
class KafkaErrorHandler(
    private val failedEventRepository: FailedEventRepository
) : CommonErrorHandler {

    override fun handleRemaining(
        thrownException: Exception,
        records: MutableList<ConsumerRecord<*, *>>,
        consumer: Consumer<*, *>,
        container: MessageListenerContainer
    ) {
        for (record in records) {
            val failedEvent = FailedEventEntity(
                id = UUID.randomUUID(),
                original_event = record.value().toString(),
                error_message = thrownException.message ?: "Unknown error",
                retry_count = 3,
                topic = record.topic(),
                partition = record.partition(),
                offset = record.offset(),
                failed_at = Instant.now()
            )
            failedEventRepository.save(failedEvent)
        }
    }
}