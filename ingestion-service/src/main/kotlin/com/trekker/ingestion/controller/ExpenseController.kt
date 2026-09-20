package com.trekker.ingestion.controller

import com.trekker.ingestion.dto.ExpenseEvent
import com.trekker.ingestion.service.ExpenseProducer
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/expenses")
class ExpenseController(
    private val expenseProducer: ExpenseProducer,
) {
    @PostMapping
    fun createExpense(@Valid @RequestBody event: ExpenseEvent): ResponseEntity<Map<String, Any>> {
        expenseProducer.sendExpense(event)

        val response = mapOf(
            "status" to "ACCEPTED",
            "message" to "Expense successfully received and sent to Kafka.",
            "eventId" to event.id
        )

    return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }
}