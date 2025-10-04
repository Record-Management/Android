package see.day.data.api.dailyRecordService.json

val dailyRecordUpdateSuccessResponse = """
  {
  "statusCode": 200,
  "code": "S200",
  "message": "하루 기록이 성공적으로 수정되었습니다",
  "data": {
    "id": "b6e1b665-e1f3-4e2b-b6c1-efb9b32c7be8",
    "type": "DAILY",
    "emotion": "😍",
    "content": "수정된 하루 내용입니다. 저녁에 영화도 봤어요!",
    "imageUrls": [
      "https://seeday-images.s3.ap-northeast-2.amazonaws.com/records/images/updated-uuid.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Expires=3600&..."
    ],
    "recordDate": [
      2025,
      1,
      7
    ],
    "recordTime": [
      15,
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
    ]
  }
}
""".trimIndent()

val dailyRecordUpdateFailure400Response = """
    {
      "statusCode": 400,
      "code": "E40001",
      "message": "입력값을 확인해주세요.",
      "data": null
    }
""".trimIndent()

val dailyRecordUpdateFailure404Response = """
    {
      "statusCode": 404,
      "code": "E40406",
      "message": "존재하지 않는 기록입니다.",
      "data": null
    }
""".trimIndent()
