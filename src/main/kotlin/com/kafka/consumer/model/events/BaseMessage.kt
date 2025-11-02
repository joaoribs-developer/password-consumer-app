package com.kafka.consumer.model.events

import com.kafka.consumer.enums.Topic
import java.util.*

data class BaseMessage(
    val messageId: UUID,
    val userId: UUID,
    val topic: Topic,
    val password: String?
)
