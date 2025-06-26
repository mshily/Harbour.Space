package com.vina_esima.final_project.converters

import com.vina_esima.final_project.analytics.EnrtyDB.EntryModel
import com.vina_esima.final_project.analytics.date
import com.vina_esima.final_project.analytics.dateDay

// date

fun _toDateDay(value: String): dateDay {
    val parts = value.split(":")
    val year = parts.getOrNull(0)?.toIntOrNull() ?: 0
    val month = parts.getOrNull(1)?.toIntOrNull() ?: 0
    val day = parts.getOrNull(2)?.toIntOrNull() ?: 0

    return dateDay(year, month, day)
}

fun _fromDateDay(value: dateDay): String {
    return value.year.toString() + ":" + value.month.toString() + ":" + value.day.toString()
}

fun _fromDate(value: date): String {
    return value.year.toString() + ":" + value.month.toString() + ":" + value.day.toString() + ":" +
            value.hour.toString() + ":" + value.minute.toString() + ":" + value.second.toString()
}
fun _toDate(value: String): date {
    val parts = value.split(":")
    val year = parts.getOrNull(0)?.toIntOrNull() ?: 0
    val month = parts.getOrNull(1)?.toIntOrNull() ?: 0
    val day = parts.getOrNull(2)?.toIntOrNull() ?: 0
    val hour = parts.getOrNull(3)?.toIntOrNull() ?: 0
    val minute = parts.getOrNull(4)?.toIntOrNull() ?: 0
    val second = parts.getOrNull(5)?.toIntOrNull() ?: 0

    return date(year, month, day, hour, minute, second)
}


fun _fromEntry(value: EntryModel): String {
    return _fromDate(value.start) + ":" + _fromDate(value.end) + ":" + _fromDateDay(value.day) + ":" + value.activity
}

fun _toEntry (value: String): EntryModel {
    var idx: Int = 0

    fun read(): String {
        val sb = StringBuilder()
        while (idx < value.length && value[idx] != ':') {
            sb.append(value[idx])
            idx++
        }
        if (idx < value.length && value[idx] == ':') {
            idx++
        }
        return sb.toString()
    }
    val start = read()
    val end = read()
    val day = read()
    val activity = read()
    return EntryModel(
        id = value,
        start = _toDate(start),
        end = _toDate(end),
        day = _toDateDay(day),
        activity = activity
    )
}