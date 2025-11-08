package see.day.data.api.goalService.json

val getCurrentGoalSuccessResponse = """
        {
      "statusCode": 200,
      "code": "S20000",
      "message": "정상적으로 처리되었습니다.",
       "data": {
         "goalId": "550e8400-e29b-41d4-a716-446655440000",
          "recordType": "HABIT",
          "goalDays": 20,
       "startDate": [2025,10,16],
          "endDate": [2025,10,16],
       "completedDays": 7,
       "achievementRate": 35,
         "treeStage": "STAGE_2",
       "canCreateNew": false
    }
    }
""".trimIndent()
