package com.university.support.domain

import java.util.UUID

data class SupportRequest(
    val id: UUID = UUID.randomUUID(),
    val studentId: String,
    val topic: String,
    val text: String,
    val channel: NotificationChannel,
    val isUrgent: Boolean
)

enum class NotificationChannel {
    EMAIL, MESSENGER
}

interface RequestRepository {
    fun save(request: SupportRequest): SupportRequest
    fun existsByStudentIdAndTopic(studentId: String, topic: String): Boolean
}

interface NotificationService {
    fun sendNotification(studentId: String, message: String, channel: NotificationChannel)
}

interface Logger {
    fun info(message: String)
    fun urgent(message: String)
}
