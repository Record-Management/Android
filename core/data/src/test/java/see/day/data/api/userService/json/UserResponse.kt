package see.day.data.api.userService.json

val getUserResponse = """
    {
      "statusCode": 200,
      "code": "SUCCESS",
      "message": "요청이 성공적으로 처리되었습니다.",
      "data": {
        "id": "550e8400-e29b-41d4-a716-446655440000",
        "name": "카카오닉네임",
        "nickname": "홍길동",
        "email": "user@example.com",
        "socialType": "KAKAO",
        "mainRecordType": "EXERCISE",
        "birthDate": [1998, 6, 2],
        "goalDays": 20,
        "notificationEnabled": true,
        "onboardingCompleted": true,
        "createdAt": [2025, 9, 2, 2, 46, 41, 12321]
      }
    }
""".trimIndent()
