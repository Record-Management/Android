package see.day.data.repository

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import see.day.domain.repository.NotificationRepository
import see.day.network.NotificationService
import see.day.network.dto.CommonResponse
import see.day.network.dto.PageInfoResponse
import see.day.network.dto.notification.NotificationHistoryDataResponse
import see.day.network.dto.notification.NotificationHistoryItemResponse
import see.day.network.dto.notification.NotificationHistoryResponse
import see.day.network.dto.notification.NotificationSettingResponse
import see.day.network.dto.notification.UpdatedCountResponse
import see.day.repository.NotificationRepositoryImpl

@RunWith(MockitoJUnitRunner::class)
class NotificationRepositoryTest {

    private lateinit var sut: NotificationRepository

    @Mock
    private lateinit var notificationService: NotificationService

    @Before
    fun setUp() {
        sut = NotificationRepositoryImpl(notificationService)
    }

    @Test
    fun given_whenGetNotificationHistory_thenReturnsNotificationHistory() {
        runTest {
            // given
            val notificationResponse = NotificationHistoryResponse(
                notifications = NotificationHistoryDataResponse(
                    items = listOf(NotificationHistoryItemResponse("DAILY","설명","2025-10-27:08:10:00")),
                    pageInfo = PageInfoResponse(
                        page = 0,
                        size = 0,
                        totalElements = 0,
                        totalPages = 0
                    )
                ),
                recentCheckedAt = "2025-10-27:08:10:00"
            )

            whenever(notificationService.getNotificationHistory()).thenReturn(
                CommonResponse(
                    200,
                    "SUCCESS",
                    "요청이 성공적으로 처리되었습니다.",
                    data = notificationResponse
                )
            )

            // when
            val result = sut.getNotificationHistory().getOrThrow()

            // then
            verify(notificationService).getNotificationHistory()
        }
    }

    @Test
    fun given_whenUpdateNotificationHistoryAllRead_thenWorksFine() {
        runTest {
            // given
            whenever(notificationService.updateNotificationHistoryAllRead()).thenReturn(
                CommonResponse(
                    200,
                    "SUCCESS",
                    "모든 알림이 읽음 처리되었습니다.",
                    data = UpdatedCountResponse(5)
                )
            )

            // when
            val result = sut.updateNotificationHistoryAllRead().getOrThrow()

            // then
            verify(notificationService).updateNotificationHistoryAllRead()
        }
    }

    @Test
    fun given_whenGetNotificationSetting_thenWorksFine() {
        runTest {
            // given
            whenever(notificationService.getNotificationSetting()).thenReturn(
                CommonResponse(
                    200,
                    "SUCCESS",
                    "요청이 성공적으로 처리되었습니다.",
                    data = NotificationSettingResponse("",true,true,true,true)
                )
            )

            // when
            val result = sut.getNotificationSetting().getOrThrow()

            // then
            verify(notificationService).getNotificationSetting()
        }
    }
}
