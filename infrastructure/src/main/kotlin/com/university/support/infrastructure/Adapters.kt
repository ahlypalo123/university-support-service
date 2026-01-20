package com.university.support.infrastructure

import com.university.support.domain.*
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class InMemoryRequestRepository : RequestRepository {
    private val requests = ConcurrentHashMap<String, SupportRequest>()

    override fun save(request: SupportRequest): SupportRequest {
        requests[request.id.toString()] = request
        return request
    }

    override fun existsByStudentIdAndTopic(studentId: String, topic: String): Boolean {
        return requests.values.any { it.studentId == studentId && it.topic == topic }
    }
}

@Component
class ConsoleNotificationService : NotificationService {
    override fun sendNotification(studentId: String, message: String, channel: NotificationChannel) {
        println("Sending $channel notification to $studentId: $message")
    }
}

@Component
class ConsoleLogger : Logger {
    override fun info(message: String) {
        println("INFO: $message")
    }

    override fun urgent(message: String) {
        println("URGENT: $message")
    }
}
