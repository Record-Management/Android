package see.day.data.api.loginService.json

internal val createLoginResponse = """
    {
      "statusCode": 200,
      "code": "S200",
      "message": "요청이 성공적으로 처리되었습니다.",
      "data": {
        "user": {
          "id": "user_123456",
          "name": "홍길동",
          "email": "hong@example.com"
        },
        "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9",
        "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9",
        "newUser": true
      }
    }
""".trimIndent()

internal val oldLoginResponse = """
    {
      "statusCode": 201,
      "code": "S201",
      "message": "요청이 성공적으로 처리되었습니다.",
      "data": {
        "user": {
          "id": "user_123456",
          "name": "홍길동",
          "email": "hong@example.com"
        },
        "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        "newUser": false
      }
    }
""".trimIndent()
