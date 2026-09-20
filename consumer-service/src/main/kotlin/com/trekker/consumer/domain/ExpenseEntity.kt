package com.trekker.consumer.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "expenses")
class ExpenseEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    val amount: BigDecimal,
    val currency: String,
    val category: String,
    val description: String,
    val timestamp: Instant
)