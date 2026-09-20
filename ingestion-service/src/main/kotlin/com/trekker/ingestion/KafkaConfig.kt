package com.trekker.ingestion

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class KafkaConfig {

    @Bean
    fun rawExpenseTopic(): NewTopic {
        return TopicBuilder.name("raw-expenses")
            .partitions(1)
            .replicas(1)
            .build()
    }
}