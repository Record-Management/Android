package see.day.data.api.habitRecordService.json

val postFullFieldSuccessResponse = """
    {
      "statusCode": 200,
      "code": "S20000",
      "message": "정상적으로 처리되었습니다.",
      "data": {
        "id": "habit_record_123",
        "type": "HABIT",
        "recordDate": [2025,10,16],
        "recordTime": [14, 30, 0],
        "createdAt": [2025, 9,18,15,30,0,0],
        "updatedAt": [2025, 9,18,15,30,0,0],
        "habitType": "EXERCISE",
        "notificationEnabled": true,
        "notificationTime": [9,0,0],
        "memo": "오늘도 운동 완료!",
        "isCompleted": false,
        "isMainRecord": false
      }
    }
""".trimIndent()

val postNotificationTimeEmptyFieldSuccessResponse = """
    {
      "statusCode": 200,
      "code": "S20000",
      "message": "정상적으로 처리되었습니다.",
      "data": {
        "id": "habit_record_123",
        "type": "HABIT",
        "recordDate": [2025,10,16],
        "recordTime": [14, 30, 0],
        "createdAt": [2025, 9,18,15,30,0,0],
        "updatedAt": [2025, 9,18,15,30,0,0],
        "habitType": "EXERCISE",
        "notificationEnabled": false,
        "memo": "오늘도 운동 완료!",
        "isCompleted": false,
        "isMainRecord": false
      }
    }
""".trimIndent()
