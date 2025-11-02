package com.kafka.consumer.repository

import com.kafka.consumer.model.entity.Password
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PasswordRepository : JpaRepository<Password, UUID> {
    fun findByUserId(userId: UUID): Password?
}