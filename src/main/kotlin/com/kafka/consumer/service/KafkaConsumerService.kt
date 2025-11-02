package com.kafka.consumer.service

import com.kafka.consumer.enums.Topic
import com.kafka.consumer.model.entity.Password
import com.kafka.consumer.model.events.BaseMessage
import com.kafka.consumer.repository.PasswordRepository
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*

@Service
class KafkaConsumerService(
    private val passwordRepository: PasswordRepository,
    private val cryptoService: CryptoService
) {
    private val logger = LoggerFactory.getLogger(KafkaConsumerService::class.java)

    @KafkaListener(
        topics = ["create_temporary_password", "create_definitive_password"],
        groupId = "user-group",
        containerFactory = ""
    )
    fun consume(message: BaseMessage) {
        try {
            logger.info("Message reicevied: ${message}")
            if (message.topic == Topic.CREATE_TEMPORARY_PASSWORD)
                passwordRepository.save(
                    Password(
                        UUID.randomUUID(),
                        message.userId,
                        temporaryPassword = UUID.randomUUID().toString(),
                        expiredAt = Timestamp.valueOf(LocalDateTime.now().plusMinutes(6))
                    )
                )
            else
                passwordRepository.findByUserId(message.userId)?.let {
                    passwordRepository.save(
                        it.copy(
                            password = cryptoService.encrypt(message.password ?: ""),
                            expiredAt = Timestamp.valueOf(LocalDateTime.now().plusMonths(6)),
                            temporaryPassword = null
                        )
                    )
                }
        } catch (e: Exception) {
            logger.error("Message recived failed: ${e.message}")
        }
    }
}