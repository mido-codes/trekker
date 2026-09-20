package com.trekker.consumer.dto

import java.math.BigDecimal
import java.time.Instant

data class ExpenseEvent(
    val id: String = "",
    val amount: BigDecimal = BigDecimal.ZERO,
    val currency: String = "EUR",
    val category: String = "",
    val description: String = "",
    val timestamp: Instant = Instant.now(),
)