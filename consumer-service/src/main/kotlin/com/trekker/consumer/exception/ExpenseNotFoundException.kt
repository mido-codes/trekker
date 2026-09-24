package com.trekker.consumer.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.web.ErrorResponse
import java.util.UUID

class ExpenseNotFoundException(id: UUID) :
    RuntimeException("Expense with id $id not found"),
    ErrorResponse {

    override fun getStatusCode(): HttpStatusCode = HttpStatus.NOT_FOUND
    override fun getBody(): ProblemDetail =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.NOT_FOUND,
            message ?: "Expense not found"
        ).apply {
            title = "Expense not found"
        }
}