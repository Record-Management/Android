package see.day.data.api.calendarService.json

val getMonthlyRecordResponse = """
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

val getDetailRecordsResponse = """
    {
      "statusCode": 200,
      "code": "S200",
      "message": "일일 기록이 성공적으로 조회되었습니다",
      "data": {
        "date": "2025-01-07",
        "records": [
          {
            "id": "550e8400-e29b-41d4-a716-446655440000",
            "type": "DAILY",
            "emotion": "😊",
            "content": "오늘은 정말 좋은 하루였습니다. 아침에 운동도 하고 친구들과 맛있는 음식도 먹었어요.",
            "imageUrls": [
              "https://example.com/image1.jpg",
              "https://example.com/image2.jpg"
            ],
            "recordDate": "2025-01-07",
            "recordTime": "15:21",
            "createdAt": "2025-01-07T15:21:00",
            "updatedAt": "2025-01-07T15:21:00"
          }
        ]
      }
    }
""".trimIndent()
