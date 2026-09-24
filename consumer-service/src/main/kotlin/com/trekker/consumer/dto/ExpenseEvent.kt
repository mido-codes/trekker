package com.trekker.consumer.dto

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class ExpenseEvent(
    val id: UUID,
    val amount: BigDecimal = BigDecimal.ZERO,
    val currency: String = "EUR",
    val category: String = "",
    val description: String? = null,
    val timestamp: Instant = Instant.now(),
)