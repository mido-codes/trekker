package com.trekker.consumer.config

import com.trekker.consumer.KafkaErrorHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder
import org.springframework.kafka.support.EndpointHandlerMethod
import org.springframework.retry.annotation.EnableRetry

@Configuration
@EnableRetry
class KafkaRetryConfig {

    @Bean
    fun retryTopicConfiguration(kafkaTemplate: KafkaTemplate<String, Any>): org.springframework.kafka.retrytopic.RetryTopicConfiguration {
        val dltHandler = EndpointHandlerMethod("expenseConsumer", "dltHandler")
        return RetryTopicConfigurationBuilder.newInstance()
            .fixedBackOff(1000L)
            .maxAttempts(3)
            .dltSuffix("-DLT")
            .dltHandlerMethod(dltHandler)
            .create(kafkaTemplate)
    }

    @Bean
    fun kafkaListenerContainerFactory(
        consumerFactory: ConsumerFactory<String, String>,
        kafkaErrorHandler: KafkaErrorHandler
    ): ConcurrentKafkaListenerContainerFactory<String, String> {

        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.setConsumerFactory(consumerFactory)
        factory.setCommonErrorHandler(kafkaErrorHandler)
        return factory
    }
}
