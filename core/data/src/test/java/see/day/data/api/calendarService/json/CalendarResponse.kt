package see.day.data.api.calendarService.json

val calendarGetResponse = """
    {
      "statusCode": 200,
      "code": "S200",
      "message": "캘린더가 성공적으로 조회되었습니다",
      "data": {
        "year": 2025,
        "month": 1,
        "dailyRecords": [
          {
            "date": "2025-01-07",
            "records": [
              {
                "id": "550e8400-e29b-41d4-a716-446655440000",
                "type": "DAILY",
                "emotion": "😊"
              },
              {
                "id": "550e8400-e29b-41d4-a716-446655440001",
                "type": "EXERCISE",
                "emotion": "💪"
              }
            ]
          }
        ]
      }
    }
""".trimIndent()
