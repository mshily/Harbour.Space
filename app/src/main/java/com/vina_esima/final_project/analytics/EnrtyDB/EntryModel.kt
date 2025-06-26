package com.vina_esima.final_project.analytics.EnrtyDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.vina_esima.final_project.analytics.date
import com.vina_esima.final_project.analytics.dateDay
import com.vina_esima.final_project.converters._fromDate
import com.vina_esima.final_project.converters._fromDateDay
import com.vina_esima.final_project.converters._toDate
import com.vina_esima.final_project.converters._toDateDay

object dateConverter {
    @TypeConverter
    fun fromdate(value: date): String {
        return _fromDate(value)
    }

    @TypeConverter
    fun todate(value: String): date {
        return _toDate(value)
    }

    @TypeConverter
    fun fromDateDay(value: dateDay): String {
        return _fromDateDay(value)
    }

    @TypeConverter
    fun toDateDay(value: String): dateDay {
        return _toDateDay(value)
    }
}

@Entity(tableName = "entry")
class EntryModel(
    @PrimaryKey(autoGenerate = false)
    val id: String,

    @ColumnInfo(name = "start")
    val start: date,

    @ColumnInfo(name = "end")
    val end: date,

    @ColumnInfo(name = "day")
    val day: dateDay,

    @ColumnInfo(name = "activity")
    val activity: String,

)