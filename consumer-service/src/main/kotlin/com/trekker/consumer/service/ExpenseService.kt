package com.trekker.consumer.service

import com.trekker.consumer.domain.ExpenseRepository
import com.trekker.consumer.dto.ExpenseEvent
import com.trekker.consumer.exception.ExpenseNotFoundException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ExpenseService(
    private val expenseRepository: ExpenseRepository,
) {

    fun getAll(): List<ExpenseEvent> {
        return expenseRepository.findAll().map { entity ->
            ExpenseEvent(
                id = entity.id,
                amount = entity.amount,
                currency = entity.currency,
                category = entity.category,
                description = entity.description,
                timestamp = entity.timestamp,
            )
        }
    }

    fun getById(id: UUID): ExpenseEvent {
        val entity = expenseRepository.findById(id)
            .orElseThrow { throw ExpenseNotFoundException(id) }
        return ExpenseEvent(
            id = entity.id,
            amount = entity.amount,
            currency = entity.currency,
            category = entity.category,
            description = entity.description,
            timestamp = entity.timestamp,
        )
    }
}