package com.vina_esima.final_project.analytics.EnrtyDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.vina_esima.final_project.converters._fromEntry
import com.vina_esima.final_project.converters._toEntry

object CalendarConverter {
    @TypeConverter
    fun fromEntry(value: EntryModel): String {
        return _fromEntry(value)
    }

    @TypeConverter
    fun toEntry(value: String): EntryModel {
        return _toEntry(value)
    }
}


@Entity (tableName = "calender")
class CalendarModel (
    @PrimaryKey (autoGenerate = false)
    val id: String,

    @ColumnInfo(name = "year")
    val year: Int,

    @ColumnInfo(name = "month")
    val month: Int,

    @ColumnInfo(name = "day")
    val day: Int,

    @ColumnInfo(name = "entry")
    val entry: EntryModel,
)
