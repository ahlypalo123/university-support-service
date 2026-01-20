package com.university.support.application

import com.university.support.domain.*
import org.springframework.stereotype.Service

@Service
class RequestService(
    private val repository: RequestRepository,
    private val notificationService: NotificationService,
    private val logger: Logger
) {
    fun processRequest(
        studentId: String,
        topic: String,
        text: String,
        channel: NotificationChannel,
        isUrgent: Boolean
    ): String {
        if (studentId.isBlank() || topic.isBlank() || text.isBlank()) {
            throw IllegalArgumentException("Bad request: missing required fields")
        }

        if (isUrgent) {
            logger.urgent("URGENT: $studentId")
        }

        if (repository.existsByStudentIdAndTopic(studentId, topic)) {
            logger.info("Duplicate request: $studentId")
            return "Already exists"
        }

        val request = SupportRequest(
            studentId = studentId,
            topic = topic,
            text = text,
            channel = channel,
            isUrgent = isUrgent
        )

        val savedRequest = repository.save(request)
        
        notificationService.sendNotification(
            studentId, 
            "Created request #${savedRequest.id}", 
            channel
        )

        logger.info("Created request id=${savedRequest.id}")

        return when {
            topic.contains("password", ignoreCase = true) -> "Reset instruction sent"
            topic.contains("schedule", ignoreCase = true) -> "We will check schedule"
            else -> "Request accepted"
        }
    }
}
