package com.trekker.consumer.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ExpenseRepository : JpaRepository<ExpenseEntity, UUID>