package see.day.data.api.calendarService.json

val getMonthlyRecordResponse = """
    {
      "statusCode": 200,
      "code": "S200",
      "message": "캘린더가 성공적으로 조회되었습니다",
      "data": {
        "year": 2025,
        "month": 1,
        "monthlyRecords": [
          {
            "date": [2025, 1, 7],
            "mainRecordTypeForDate": "HABIT",
            "records": [
              {
                "id": "550e8400-e29b-41d4-a716-446655440000",
                "type": "DAILY"
              },
              {
                "id": "550e8400-e29b-41d4-a716-446655440001",
                "type": "EXERCISE"
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
        "date": [2025, 1, 7],
        "records": [
            {
                "id": "550e8400-e29b-41d4-a716-446655440000",
                "type": "DAILY",
                "recordDate": [2025, 1, 7],
                "recordTime": [15, 21],
                "createdAt": [2025,1,7,15,21,0],
                "updatedAt": [2025,1,7,15,21,0],
                "imageUrls": [],
                "emotion": "😊",
                "content": "오늘은 정말 좋은 하루였습니다."
            },
            {
                "id": "660e8400-e29b-41d4-a716-446655440001",
                "type": "EXERCISE",
                "recordDate": [2025,1,7],
                "recordTime": [15, 21],
                "createdAt": [2025,1,7,16,30,0],
                "updatedAt": [2025,1,7,16,30,0],
                "imageUrls": [ ],
                "exerciseType": "CARDIO",
                "exerciseTimeMinutes": 30,
                "stepCount": 5000,
                "weight": 70.5,
                "caloriesBurned": 300,
                "dailyNote": "오늘 운동 너무 힘들었지만 뿌듯해요!"
            }
        ]
      }
    }
""".trimIndent()
