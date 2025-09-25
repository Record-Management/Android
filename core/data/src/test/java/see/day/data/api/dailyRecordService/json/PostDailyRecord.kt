package see.day.data.api.dailyRecordService.json

val dailyRecordDetailResponse = """
{
  "code": "S201",
  "statusCode": 201,
  "message": "하루 기록이 성공적으로 작성되었습니다",
  "data": {
    "id": "b6e1b665-e1f3-4e2b-b6c1-efb9b32c7be8",
    "type": "DAILY",
    "emotion": "😊",
    "content": "오늘은 정말 좋은 하루였다. 친구들과 맛있는 음식도 먹고 운동도 했어요.",
    "imageUrls": [
      "https://seeday-images.s3.ap-northeast-2.amazonaws.com/records/images/uuid1.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Expires=3600&...",
      "https://seeday-images.s3.ap-northeast-2.amazonaws.com/records/images/uuid2.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Expires=3600&..."
    ],
    "recordDate": [2025,1,7],
    "recordTime": [15,30],
    "createdAt": [2025,9,18,15,30,0,0],
    "updatedAt": [2025,9,18,15,30,0,0]
  }
}
""".trimIndent()

val dailyRecordDetailOverResponse = """
    {
      "statusCode": 400,
      "code": "E40407",
      "message": "하루에 등록할 수 있는 일상 기록은 최대 2개입니다.",
      "data": null
    }
""".trimIndent()
