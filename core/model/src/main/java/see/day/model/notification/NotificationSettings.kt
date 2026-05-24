package see.day.model.notification

data class NotificationSettings(
    val userId: String,
    val dailyRecordEnabled: Boolean,
    val exerciseRecordEnabled: Boolean,
    val habitRecordEnabled: Boolean,
    val goalSettingNotificationEnabled: Boolean,
//    val scheduleNotificationEnabled: Boolean // [TODO] 이름 변경될 수 있음
)
