package see.day.data.api.scheduleService.json

const val postScheduleFullFieldSuccessResponse = """
{
  "scheduleRecordId": "schedule-record-id",
  "title": "매장 점검",
  "startDate": "2026-03-21",
  "endDate": "2026-03-21",
  "notificationType": "ONE_DAY_BEFORE",
  "notificationCustomHours": 9,
  "notificationCustomMinutes": 30,
  "repeatType": "NONE",
  "repeatEndsOn": "2026-08-10",
  "location": "도쿄점",
  "color": "ORANGE",
  "memo": "오픈 전 냉장고 점검"
}
"""

const val postScheduleRequiredFieldSuccessResponse = """
{
  "scheduleRecordId": "schedule-record-id",
  "title": "매장 점검",
  "startDate": "2026-03-21",
  "endDate": "2026-03-21",
  "notificationType": "NONE",
  "repeatType": "NONE",
  "color": "ORANGE"
}
"""

const val postScheduleCustomNotificationSuccessResponse = """
{
  "scheduleRecordId": "schedule-record-id",
  "title": "매장 점검",
  "startDate": "2026-03-21",
  "endDate": "2026-03-21",
  "notificationType": "CUSTOM",
  "notificationCustomHours": 23,
  "notificationCustomMinutes": 59,
  "repeatType": "NONE",
  "color": "ORANGE"
}
"""

const val postScheduleBadRequestResponse = """
{
  "statusCode": 400,
  "code": "C400",
  "message": "잘못된 일정 요청입니다.",
  "data": null
}
"""

const val getScheduleFullFieldSuccessResponse = """
{
  "scheduleRecordId": "schedule-record-id",
  "userId": "user-id",
  "title": "매장 점검",
  "startDate": [2026, 3, 21],
  "endDate": [2026, 3, 21],
  "notificationType": "CUSTOM",
  "notificationCustomHours": 9,
  "notificationCustomMinutes": 30,
  "repeatType": "NONE",
  "repeatEndsOn": [2026, 8, 10],
  "location": "도쿄점",
  "color": "ORANGE",
  "memo": "오픈 전 냉장고 점검",
  "createdAt": "2026-03-20T10:00:00",
  "updatedAt": "2026-03-20T10:30:00"
}
"""

const val getScheduleBadRequestResponse = """
{
  "statusCode": 400,
  "code": "C400",
  "message": "존재하지 않는 일정입니다.",
  "data": null
}
"""
