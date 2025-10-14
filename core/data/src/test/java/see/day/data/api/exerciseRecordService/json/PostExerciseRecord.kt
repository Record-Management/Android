package see.day.data.api.exerciseRecordService.json

val postFullFieldSuccessResponse = """
    {
      "code": "S201",
      "statusCode": 201,
      "message": "운동기록이 성공적으로 작성되었습니다",
      "data": {
        "id": "b6e1b665-e1f3-4e2b-b6c1-efb9b32c7be8",
        "type": "EXERCISE",
        "recordDate": [
          2025,
          1,
          7
        ],
        "recordTime": [
          13,
          30
        ],
        "createdAt": [
          2025,
          9,
          18,
          15,
          30,
          0,
          0
        ],
        "updatedAt": [
          2025,
          9,
          18,
          15,
          30,
          0,
          0
        ],
        "exerciseType": "RUNNING",
        "caloriesBurned": 100,
        "exerciseTimeMinutes": 100,
        "stepCount": 100,
        "weight": 100.5,
        "dailyNote": "오늘 농구 경기 정말 재밌었다! 팀워크가 좋았어요.",
        "imageUrls": [
          "https://seeday-images.s3.ap-northeast-2.amazonaws.com/records/images/basketball-uuid.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Expires=3600&..."
        ]
      }
    }
""".trimIndent()

val postCaloriesNullFieldSuccessResponse = """
    {
      "code": "S201",
      "statusCode": 201,
      "message": "운동기록이 성공적으로 작성되었습니다",
      "data": {
        "id": "b6e1b665-e1f3-4e2b-b6c1-efb9b32c7be8",
        "type": "EXERCISE",
        "recordDate": [
          2025,
          1,
          7
        ],
        "recordTime": [
          13,
          30
        ],
        "createdAt": [
          2025,
          9,
          18,
          15,
          30,
          0,
          0
        ],
        "updatedAt": [
          2025,
          9,
          18,
          15,
          30,
          0,
          0
        ],
        "exerciseType": "RUNNING",
        "exerciseTimeMinutes": 100,
        "stepCount": 100,
        "weight": 100.5,
        "dailyNote": "오늘 농구 경기 정말 재밌었다! 팀워크가 좋았어요.",
        "imageUrls": [
          "https://seeday-images.s3.ap-northeast-2.amazonaws.com/records/images/basketball-uuid.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Expires=3600&..."
        ]
      }
    }
""".trimIndent()

val postOverLimitExerciseRecordFailureResponse = """
    {
      "statusCode": 400,
      "code": "E40408",
      "message": "하루에 등록할 수 있는 운동 기록은 최대 1개입니다.",
      "data": null
    }
""".trimIndent()

val postOverLimitRecordsFailureResponse = """
    {
      "statusCode": 400,
      "code": "E40410",
      "message": "하루에 등록할 수 있는 기록 종류는 최대 2가지입니다.",
      "data": null
    }
""".trimIndent()

val updateExerciseRequestSuccessResponse = """
    {
      "code": "S200",
      "statusCode": 200,
      "message": "운동기록이 성공적으로 수정되었습니다",
      "data": {
        "id": "b6e1b665-e1f3-4e2b-b6c1-efb9b32c7be8",
        "type": "EXERCISE",
        "recordDate": [
          2025,
          1,
          7
        ],
        "recordTime": [
          13,
          30
        ],
        "createdAt": [
          2025,
          9,
          18,
          15,
          30,
          0,
          0
        ],
        "updatedAt": [
          2025,
          9,
          18,
          15,
          30,
          0,
          0
        ],
        "exerciseType": "RUNNING",
        "caloriesBurned": 100,
        "exerciseTimeMinutes": 100,
        "stepCount": 100,
        "weight": 100.5,
        "dailyNote": "오늘 농구 경기 정말 재밌었다! 팀워크가 좋았어요.",
        "imageUrls": [
          "https://seeday-images.s3.ap-northeast-2.amazonaws.com/records/images/basketball-uuid.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Expires=3600&..."
        ]
      }
    }
""".trimIndent()
