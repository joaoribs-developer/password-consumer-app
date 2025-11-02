package com.kafka.consumer.model.entity


import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.sql.Timestamp
import java.util.*

@Entity(name = "passwords")
data class Password(

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    val id: UUID,

    @Column(name = "user_id", updatable = false, nullable = false)
    var userId: UUID,

    @Column(name = "password", length = 255)
    var password: String? = null,

    @Column(name = "temporary_password", length = 255)
    var temporaryPassword: String? = null,

    @Column(name = "expired_at")
    var expiredAt: Timestamp?
)
