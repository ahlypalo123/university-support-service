package com.university.support.application

import com.university.support.domain.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.UUID

class RequestServiceTest {

    private lateinit var repository: RequestRepository
    private lateinit var notificationService: NotificationService
    private lateinit var logger: Logger
    private lateinit var requestService: RequestService

    @BeforeEach
    fun setup() {
        repository = mock(RequestRepository::class.java)
        notificationService = mock(NotificationService::class.java)
        logger = mock(Logger::class.java)
        requestService = RequestService(repository, notificationService, logger)
    }

    @Test
    fun `should process new request successfully`() {
        // Given
        val studentId = "123"
        val topic = "General question"
        val text = "Help me"
        val channel = NotificationChannel.EMAIL
        
        `when`(repository.existsByStudentIdAndTopic(studentId, topic)).thenReturn(false)
        `when`(repository.save(any())).thenAnswer { it.arguments[0] }

        // When
        val result = requestService.processRequest(studentId, topic, text, channel, false)

        // Then
        assertEquals("Request accepted", result)
        verify(repository).save(any())
        verify(notificationService).sendNotification(eq(studentId), anyString(), eq(channel))
    }

    @Test
    fun `should detect duplicate request`() {
        // Given
        val studentId = "123"
        val topic = "Duplicate topic"
        
        `when`(repository.existsByStudentIdAndTopic(studentId, topic)).thenReturn(true)

        // When
        val result = requestService.processRequest(studentId, topic, "text", NotificationChannel.EMAIL, false)

        // Then
        assertEquals("Already exists", result)
        verify(repository, never()).save(any())
    }
}
