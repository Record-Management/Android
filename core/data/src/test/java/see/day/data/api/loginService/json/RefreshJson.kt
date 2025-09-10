package see.day.data.api.loginService.json

val refreshJson = """
{
    "statusCode": 200,
    "code": "S200",
    "message": "요청이 성공적으로 처리되었습니다.",
    "data": {
      "accessToken": "new_token",
      "expiresIn": 3600,
      "user": {
        "id": "user_id",
        "name": "이름",
        "email": null,
        "onboardingCompleted": false
      }
    }
}
""".trimIndent()
