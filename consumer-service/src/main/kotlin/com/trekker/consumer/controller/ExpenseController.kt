package com.trekker.consumer.controller

import com.trekker.consumer.dto.ExpenseEvent
import com.trekker.consumer.service.ExpenseService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/expenses")
class ExpenseController(
    val expenseService: ExpenseService
) {

    @GetMapping
    fun getAll(): ResponseEntity<List<ExpenseEvent>> {
        return ResponseEntity.ok(expenseService.getAll())
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): ResponseEntity<ExpenseEvent> {
        return ResponseEntity.ok(expenseService.getById(id))
    }
}
