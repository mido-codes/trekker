package com.trekker.ingestion.service

import com.trekker.ingestion.dto.ExpenseEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class ExpenseProducer(
    private val kafkaTemplate: KafkaTemplate<String, ExpenseEvent>
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun sendExpense(event: ExpenseEvent) {
        logger.info("Sent ExpenseEvent to Kafka: ID={}", event.id)

        kafkaTemplate.send("raw-expenses", event.id.toString(), event)
            .whenComplete { result, ex ->
                if (ex == null) {
                    logger.info("Event successfully sent! Partition={}, Offset{}",
                        result.recordMetadata.partition(),
                        result.recordMetadata.offset())
                } else {
                    logger.error("Error while sending ExpenseEvent: {}", ex.message, ex)
                }
            }
    }
}