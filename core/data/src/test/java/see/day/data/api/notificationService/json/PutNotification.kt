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
