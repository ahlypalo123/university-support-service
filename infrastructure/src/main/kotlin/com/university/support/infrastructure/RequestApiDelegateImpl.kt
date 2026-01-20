package com.university.support.infrastructure

import com.university.support.application.RequestService
import com.university.support.domain.NotificationChannel
import com.university.support.infrastructure.api.RequestApiDelegate
import com.university.support.infrastructure.model.SupportRequest
import com.university.support.infrastructure.model.SupportResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class RequestApiDelegateImpl(
    private val requestService: RequestService
) : RequestApiDelegate {

    override fun createRequest(supportRequest: SupportRequest): ResponseEntity<SupportResponse> {
        val channel = when (supportRequest.channel) {
            SupportRequest.Channel.email -> NotificationChannel.EMAIL
            SupportRequest.Channel.messenger -> NotificationChannel.MESSENGER
        }

        val result = requestService.processRequest(
            studentId = supportRequest.studentId,
            topic = supportRequest.topic,
            text = supportRequest.text,
            channel = channel,
            isUrgent = supportRequest.urgentFlag
        )

        return ResponseEntity.ok(SupportResponse(message = result))
    }
}
