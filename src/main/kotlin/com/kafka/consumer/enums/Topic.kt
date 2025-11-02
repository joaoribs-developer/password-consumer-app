package com.kafka.consumer.enums

enum class Topic(val topicName: String) {
    CREATE_TEMPORARY_PASSWORD("create_temporary_password"),
    CREATE_DEFINITIVE_PASSWORD("create_definitive_password")
}