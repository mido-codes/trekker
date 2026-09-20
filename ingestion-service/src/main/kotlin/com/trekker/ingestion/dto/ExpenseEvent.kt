package com.trekker.ingestion.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class ExpenseEvent(
    val id: String = UUID.randomUUID().toString(),

    @field:NotNull(message = "Expense event must not be null")
    @field:Positive(message = "Expense event must be positive")
    val amount: BigDecimal,

    @field:NotBlank(message = "Currency must be set")
    val currency: String = "EUR",

    @field:NotBlank(message = "Category must be set")
    val category: String,

    val description: String? = null,

    val timestamp: Instant = Instant.now(),
    )
