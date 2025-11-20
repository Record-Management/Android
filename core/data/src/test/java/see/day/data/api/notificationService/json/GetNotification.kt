package see.day.data.api.notificationService.json

val getNotificationHistorySuccessResponse = """
    {
      "statusCode": 200,
      "code": "SUCCESS",
      "message": "요청이 성공적으로 처리되었습니다.",
      "data": {
        "notifications": {
          "items": [
            {
              "id": "123",
              "type": "DAILY_RECORD_REMINDER",
              "title": "꾸준한 운동 기록으로 건강한 습관을 만들어보세요!",
              "message": "asdasd",
              "sentAt": [2025,10,23,20,0,0],
              "read": true
            },
            {
              "id": "123",
              "type": "GOAL_SETTING_REMINDER",
              "title": "오늘의 소중한 순간을 기록으로 남겨보세요!",
              "message": "asdasd",
              "sentAt": [2025,10,23,19,0,0],
              "read": true
            }
          ],
          "pageInfo": {
            "page": 0,
            "size": 20,
            "totalElements": 2,
            "totalPages": 1
          }
        },
        "recentCheckedAt": [2025,10,25,16,45,30]
      }
    }
""".trimIndent()

val getNotificationSettingSuccessResponse = """
    {
      "statusCode": 200,
      "code": "SUCCESS",
      "message": "요청이 성공적으로 처리되었습니다.",
      "data": {
        "userId": "550e8400-e29b-41d4-a716-446655440000",
        "dailyRecordNotificationEnabled": true,
        "exerciseNotificationEnabled": true,
        "habitNotificationEnabled": false,
        "goalSettingNotificationEnabled": true
      }
    }
""".trimIndent()
