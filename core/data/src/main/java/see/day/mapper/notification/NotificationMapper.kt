package see.day.mapper.notification

import see.day.model.notification.NotificationHistory
import see.day.model.notification.NotificationHistoryList
import see.day.model.notification.NotificationSettings
import see.day.model.notification.NotificationSettingsEdit
import see.day.model.record.RecordType
import see.day.network.dto.notification.NotificationHistoryItemResponse
import see.day.network.dto.notification.NotificationHistoryResponse
import see.day.network.dto.notification.NotificationSettingResponse
import see.day.network.dto.notification.NotificationSettingsEditRequest

fun NotificationHistoryResponse.toModel(): NotificationHistoryList {
    return NotificationHistoryList(
        notifications = this.notifications.items.toModel(),
        recentCheckedAt = recentCheckedAt
    )
}

fun List<NotificationHistoryItemResponse>.toModel(): List<NotificationHistory> {
    return this.map { it.toModel() }
}

fun NotificationHistoryItemResponse.toModel(): NotificationHistory {
    return NotificationHistory(
        id = id,
        type = type,
        title = title,
        message = message,
        sentAt = sentAt,
        isRead = isRead
    )
}

fun NotificationSettingResponse.toModel(): NotificationSettings {
    return NotificationSettings(
        userId = userId,
        dailyRecordEnabled = dailyRecordNotificationEnabled,
        exerciseRecordEnabled = exerciseNotificationEnabled,
        habitRecordEnabled = habitNotificationEnabled,
        goalSettingNotificationEnabled = goalSettingNotificationEnabled
    )
}

fun NotificationSettingsEdit.toDto() : NotificationSettingsEditRequest {
    return NotificationSettingsEditRequest(
        dailyRecordNotificationEnabled = dailyRecordNotificationEnabled,
        exerciseNotificationEnabled = exerciseNotificationEnabled,
        habitNotificationEnabled = habitNotificationEnabled,
        goalSettingNotificationEnabled = goalSettingNotificationEnabled
    )
}
