package com.trekker.consumer.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "failed_events")
class FailedEventEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    val original_event: String,
    val error_message: String,
    val retry_count: Int,
    val topic: String,
    val partition: Int,
    @Column(name = "\"offset\"")
    val offset: Long,
    val failed_at: Instant,
)