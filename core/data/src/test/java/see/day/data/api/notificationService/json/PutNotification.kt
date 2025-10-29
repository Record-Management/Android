package see.day.data.api.notificationService.json

val putNotificationMarkReadSuccessResponse = """
    {
      "statusCode": 200,
      "code": "SUCCESS",
      "message": "모든 알림이 읽음 처리되었습니다.",
      "data": {
        "updatedCount": 3
      }
    }
""".trimIndent()

val putNotificationSettingSuccessResponse = """
    {
      "statusCode": 200,
      "code": "SUCCESS",
      "message": "요청이 성공적으로 처리되었습니다.",
      "data": {
        "userId": "550e8400-e29b-41d4-a716-446655440000",
        "dailyRecordNotificationEnabled": true,
        "exerciseNotificationEnabled": true,
        "habitNotificationEnabled": true,
        "noGoalNotificationEnabled": true
      }
    }
""".trimIndent()
