package com.trekker.consumer.service

import com.trekker.consumer.domain.ExpenseEntity
import com.trekker.consumer.domain.ExpenseRepository
import com.trekker.consumer.dto.ExpenseEvent
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.kafka.annotation.DltHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ExpenseConsumer(
    private val expenseRepository: ExpenseRepository
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["raw-expenses"], groupId = "expense-processing-group")
    fun consume(event: ExpenseEvent) {
        val eventId = UUID.fromString(event.id)

        if (expenseRepository.existsById(eventId)) {
            log.info("Event ${event.id} is already consumed.")
            return
        }

        log.info("consuming expense event for ID: {}", event.id)

        val entity = ExpenseEntity(
            id = eventId,
            amount = event.amount,
            currency = event.currency,
            category = event.category,
            description = event.description,
            timestamp = event.timestamp,
        )

        try {
            expenseRepository.save(entity)
            log.info("Successfully saved in PostgreSQL")
        } catch (e: DataIntegrityViolationException) {
            log.warn("Duplikat von Datenbank abgefangen für Event-ID: ${event.id}")
        }
    }

    @DltHandler
    fun dltHandler(
        event: ExpenseEvent,
        @Header(KafkaHeaders.RECEIVED_TOPIC) topic: String,
        @Header(KafkaHeaders.RECEIVED_PARTITION) partition: Int,
        @Header(KafkaHeaders.OFFSET) offset: Long
    ) {
        log.error(
            "Received event in DLT: id={}, topic={}, partition={}, offset={}",
            event.id, topic, partition, offset
        )
    }
}
