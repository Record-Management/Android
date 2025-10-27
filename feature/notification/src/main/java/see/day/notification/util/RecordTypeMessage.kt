package see.day.notification.util

import see.day.model.record.RecordType

fun RecordType.historyMessage() : String {
    return when(this) {
        RecordType.DAILY -> "아직 '하루 기록'을 작성하지 않았어요. 하루의 작은 순간이 쌓이면 큰 변화가 돼요."
        RecordType.EXERCISE -> "아직 '운동 기록'을 작성하지 않았어요. 기록이 쌓일수록 습관이 되고, 어느새 운동이 자연스러워질 거에요."
        RecordType.HABIT -> "아직 '습관 기록'을 작성하지 않았어요. 꾸준히 쌓이는 하루가 큰 변화를 만들 수 있어요."
    }
}
