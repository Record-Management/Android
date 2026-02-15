package see.day.model.notification

enum class NotificationType {
    DAILY_RECORD_REMINDER, // - 메인 기록 미등록 알림
    EXERCISE_REMINDER, // - 운동 기록 미등록 알림
    HABIT_REMINDER, // - 습관 기록 미등록 알림
    HABIT_TIME_BASED_REMINDER, // 습관 시간 지정 알림
    GOAL_SETTING_REMINDER, // - 목표 미설정 알림
    SYSTEM_ANNOUNCEMENT, // - 시스템 공지사항
    TEST, // - 테스트 알림
}
