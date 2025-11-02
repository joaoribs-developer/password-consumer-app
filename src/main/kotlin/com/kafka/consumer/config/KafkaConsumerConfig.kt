package com.kafka.consumer.config

import com.kafka.consumer.model.events.BaseMessage
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.UUIDDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer
import java.util.*

@Configuration
@EnableKafka
class KafkaConsumerConfig {

    @Value("\${user_app.kafka.servers_config}")
    lateinit var kafkaBootstrapServer: String

    @Bean
    fun consumerFactory(): ConsumerFactory<UUID, BaseMessage> {
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaBootstrapServer
        configProps[ConsumerConfig.GROUP_ID_CONFIG] = "user-group"

        configProps[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = ErrorHandlingDeserializer::class.java
        configProps[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = ErrorHandlingDeserializer::class.java

        configProps[ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS] = UUIDDeserializer::class.java
        configProps[ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS] = JsonDeserializer::class.java

        configProps[JsonDeserializer.USE_TYPE_INFO_HEADERS] = false
        configProps[JsonDeserializer.VALUE_DEFAULT_TYPE] = "com.kafka.consumer.model.events.BaseMessage"
        configProps[JsonDeserializer.TRUSTED_PACKAGES] = "com.kafka.consumer.model.events"

        return DefaultKafkaConsumerFactory(configProps)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<UUID, BaseMessage> {
        val consumerFactory = ConcurrentKafkaListenerContainerFactory<UUID, BaseMessage>()
        consumerFactory.consumerFactory = consumerFactory()
        consumerFactory.setConcurrency(2)
        return consumerFactory
    }
}