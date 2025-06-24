package com.vina_esima.final_project.analytics

const val FIRST_YEAR = 2023


class Calendar {
    val years: List <year> = TODO()

}
class year {
    val year: Int = 0
    val months: List <month> = TODO()
}
class month {
    val month: Int = 0
    val days: List <day> = TODO()
}
class day {
    val day: Int = 0
    val hours: List <entry> = TODO()
}

class dateInDay {
    val hour: Int = 0
    val minutes: Int = 0
    val seconds: Int = 0
}

class entry {
    val start: dateInDay = TODO()
    val end: dateInDay = TODO()
    val activity: String = ""
}

