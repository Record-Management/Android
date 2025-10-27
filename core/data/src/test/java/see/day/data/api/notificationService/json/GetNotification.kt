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
              "mainRecordType": "EXERCISE",
              "description": "꾸준한 운동 기록으로 건강한 습관을 만들어보세요!",
              "sentAt": [2025,10,23,20,0,0]
            },
            {
              "mainRecordType": "DAILY",
              "description": "오늘의 소중한 순간을 기록으로 남겨보세요!",
              "sentAt": [2025,10,23,19,0,0]
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
