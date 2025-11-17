package see.day.data.api.goalService.json

val getCurrentPeriodSuccessResponse = """
    {
      "statusCode": 200,
      "code": "S200",
      "message": "목표 달성 보고서 조회가 성공적으로 완료되었습니다",
      "data": {
        "currentPeriod": {
          "goalId": "550e8400-e29b-41d4-a716-446655440000",
          "recordType": "HABIT",
          "goalDays": 20,
          "startDate": [2025,11,01],
          "endDate": [2025,11,20],
          "completedDays": 7,
          "achievementRate": 35,
          "treeStage": "STAGE_2",
          "isInProgress": true
        },
        "cumulativeAchievementCount": 3,
        "recentHistory": [
          {
            "goalId": "550e8400-e29b-41d4-a716-446655440003",
            "recordType": "EXERCISE",
            "goalDays": 10,
            "startDate": [2025,10,20],
            "endDate": [2025,10,29],
            "completedDays": 10,
            "achievementRate": 100,
            "finalTreeStage": "STAGE_4",
            "status": "완료"
          },
          {
            "goalId": "550e8400-e29b-41d4-a716-446655440004",
            "recordType": "DAILY",
            "goalDays": 30,
            "startDate": [2025,09,15],
            "endDate": [2025,10,14],
            "completedDays": 28,
            "achievementRate": 93.3,
            "finalTreeStage": "STAGE_4",
            "status": "완료"
          }
        ]
      }
    }
""".trimIndent()

val getCurrentPeriodIsNullSuccessResponse = """
    {
      "statusCode": 200,
      "code": "S200",
      "message": "목표 달성 보고서 조회가 성공적으로 완료되었습니다",
      "data": {
        "currentPeriod": null,
        "cumulativeAchievementCount": 3,
        "recentHistory": [
          {
            "goalId": "550e8400-e29b-41d4-a716-446655440003",
            "recordType": "EXERCISE",
            "goalDays": 10,
            "startDate": [2025,10,20],
            "endDate": [2025,10,29],
            "completedDays": 10,
            "achievementRate": 100,
            "finalTreeStage": "STAGE_4",
            "status": "완료"
          },
          {
            "goalId": "550e8400-e29b-41d4-a716-446655440004",
            "recordType": "DAILY",
            "goalDays": 30,
            "startDate": [2025,09,15],
            "endDate": [2025,10,14],
            "completedDays": 28,
            "achievementRate": 93.3,
            "finalTreeStage": "STAGE_4",
            "status": "완료"
          }
        ]
      }
    }
""".trimIndent()
